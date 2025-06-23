package com.example.moonrider.moonrider.service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.moonrider.moonrider.dto.ContectResponse;
import com.example.moonrider.moonrider.dto.IdentityResponse;
import com.example.moonrider.moonrider.entity.Contact;
import com.example.moonrider.moonrider.entity.LinkPrecedence;
import com.example.moonrider.moonrider.repository.ContactRepository;

@Service
public class IdentityService {

    @Autowired
    private ContactRepository contactRepository;

    public IdentityResponse identify(String email, String phoneNumber) {
        List<Contact> matches = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

     // CASE 1: No match → create new primary
        if (matches.isEmpty()) {
            Contact newContact = new Contact();
            newContact.setEmail(email);
            newContact.setPhoneNumber(phoneNumber);
            newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
            contactRepository.save(newContact);

            return wrapResponse(newContact, List.of(newContact));
        }
        
     // CASE 2: There are matches
        // Find oldest primary (by createdAt)
        Contact primary = matches.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElse(matches.get(0));

     // Check if the exact contact already exists
        boolean alreadyExists = matches.stream().anyMatch(c ->
            Objects.equals(c.getEmail(), email) && Objects.equals(c.getPhoneNumber(), phoneNumber)
        );

     // If it doesn't exist, create a new secondary
        if (!alreadyExists) {
            Contact newSecondary = new Contact();
            newSecondary.setEmail(email);
            newSecondary.setPhoneNumber(phoneNumber);
            newSecondary.setLinkPrecedence(LinkPrecedence.SECONDARY);
            newSecondary.setLinkedId(primary.getId());
            contactRepository.save(newSecondary);
            matches.add(newSecondary);
        }

        // Downgrade any primary that isn’t the chosen one
        for (Contact contact : matches) {
            if (contact.getLinkPrecedence() == LinkPrecedence.PRIMARY &&
                !Objects.equals(contact.getId(), primary.getId())) {
                contact.setLinkPrecedence(LinkPrecedence.SECONDARY);
                contact.setLinkedId(primary.getId());
                contactRepository.save(contact);
            }
        }

        return wrapResponse(primary, matches);
    }

    private IdentityResponse wrapResponse(Contact primary, List<Contact> allContacts) {
        Set<String> emails = allContacts.stream()
                .map(Contact::getEmail).filter(Objects::nonNull).collect(Collectors.toSet());

        Set<String> phones = allContacts.stream()
                .map(Contact::getPhoneNumber).filter(Objects::nonNull).collect(Collectors.toSet());

        List<Long> secondaryIds = allContacts.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.SECONDARY)
                .map(Contact::getId)
                .collect(Collectors.toList());

        ContectResponse cr = new ContectResponse(primary.getId(), emails, phones, secondaryIds);
        return new IdentityResponse(cr);
    }
}

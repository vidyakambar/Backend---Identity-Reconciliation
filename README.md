# 🔗 Contact Identity Reconciliation API

This Spring Boot RESTful service links and reconciles contact information (emails and phone numbers) used across multiple customer actions into a single identity. Inspired by Doc's time-traveling needs, this backend helps modern e-commerce platforms like Zamazon provide a smarter, unified customer experience.

##  Features

- Accepts email and/or phoneNumber as input.
- Identifies if the contact already exists.
- Creates new `primary` or `secondary` entries accordingly.
- Consolidates all linked contact data under a single identity.
- Ensures one true primary contact exists.

## Use Case

E-commerce users often:
- Use different emails/phones in separate orders
- Receive duplicate notifications
- Are viewed as separate users in the database

This API links such scattered contacts, avoids spamming, and builds a 360° customer view.

## Tech Stack

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL
- Lombok
- Maven





# Project Description

This project is designed to manage products and their related information. Below is the schema and relationships between the various entities in the database.

## Database Schema

### Tables

- **Admin**
    - **Fields**:
        - `id`: Int | UUID
        - `firstname`: String
        - `lastname`: String
        - `email`: String (unique index)
        - `phone_number`: String? (optional)
        - `created_at`: DateTime
        - `updated_at`: DateTime

- **Product**
    - **Fields**:
        - `id`: Int | UUID
        - `label`: String
        - `description`: String
        - `image`: BLOB
        - `created_at`: DateTime
        - `updated_at`: DateTime

- **Tag**
    - **Fields**:
        - `id`: Int | UUID
        - `label`: String
        - `description`: String
        - `created_at`: DateTime
        - `updated_at`: DateTime

- **Category**
    - **Fields**:
        - `id`: Int | UUID
        - `label`: String
        - `description`: String
        - `created_at`: DateTime
        - `updated_at`: DateTime

### Relationships

- **Admin to Product**: One-to-Many
    - An admin can have many products.
    - Each product belongs to one admin.

- **Product to Tag**: Many-to-Many
    - A tag can be associated with multiple products.
    - A product can have multiple tags.

- **Category to Product**: One-to-Many
    - A category can have multiple products.
    - Each product belongs to one category.

### UI Fragments

- **Home**: Displays charts related to the products.
- **Products**: Lists all available products.
- **Profile**: Shows personal information of the admin.
- **Login**: Provides the authentication interface for users.

This project aims to streamline product management by efficiently categorizing and tagging products, ensuring easy access and organization for admins.

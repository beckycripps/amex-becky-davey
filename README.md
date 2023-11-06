Certainly! Here's the content translated into Markdown format:

# Gift Card API

This RESTful API allows you to manage gift cards. It provides endpoints to create, retrieve, and delete gift cards, as well as handle exceptions for resource not found and server errors.

## Endpoints

### Create a Gift Card

**Endpoint:** `POST /api/gift_cards`

**Request Body:**
```json
{
  "companyName": "Company Name",
  "value": "200.00",
  "currency": "GBP",
  "pointsCost": "1000"
}
```

**Response:** Returns the created GiftCardDto with HTTP status 201 Created.

### Get Gift Cards

**Endpoint:** `GET /api/gift_cards`

**Query Parameters:**
- `value` (optional): The value of the gift card.
- `companyName` (optional): The company name associated with the gift card.

**Response:** Returns a list of GiftCardDto objects matching the search criteria with HTTP status 200 OK.

### Get Gift Card by ID

**Endpoint:** `GET /api/gift_cards/{id}`

**Path Parameter:**
- `id`: The ID of the gift card to retrieve.

**Response:** Returns the GiftCardDto object with HTTP status 200 OK if found. If not found, returns an error response with HTTP status 404 Not Found.

### Delete Gift Card

**Endpoint:** `DELETE /api/gift_cards/{id}`

**Path Parameter:**
- `id`: The ID of the gift card to delete.

**Response:** Returns HTTP status 204 No Content upon successful deletion. If the gift card with the given ID is not found, returns an error response with HTTP status 404 Not Found.

## Error Handling

### Resource Not Found

If a requested resource is not found, the API returns an error response with HTTP status 404 Not Found.

### Server Error

If there is a server-side error during the processing of a request, the API returns an error response with HTTP status 500 Internal Server Error.

## Exception Handling

- `ResourceNotFoundException`: Thrown when a requested resource is not found. Returns an error response with HTTP status 404 Not Found.
- `ResourceNotCreatedException`: Thrown when there is an error creating a resource. Returns an error response with HTTP status 500 Internal Server Error.

## How to Use

- **Create a Gift Card:** Send a POST request to `/api/gift_cards` with the gift card details in the request body.
- **Retrieve Gift Cards:** Send a GET request to `/api/gift_cards` with optional query parameters `value` and `companyName` to filter the results.
- **Retrieve Gift Card by ID:** Send a GET request to `/api/gift_cards/{id}` where `{id}` is the ID of the gift card.
- **Delete Gift Card:** Send a DELETE request to `/api/gift_cards/{id}` where `{id}` is the ID of the gift card.

## How to Run

To run the API, ensure you have the necessary dependencies and execute the application. Access the API endpoints using an HTTP client like Postman or cURL.

Happy coding!
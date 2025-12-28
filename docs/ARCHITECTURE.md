# Architecture Documentation – Unsplash Java MCP Server

This document describes the architecture, use cases, and technical implementation of the Unsplash Model Context Protocol (MCP) server.

## Overview

The Unsplash Java MCP Server is a Spring Boot application that enables LLMs (Large Language Models) to interact with the Unsplash API. It implements the [Model Context Protocol](https://modelcontextprotocol.io), allowing any MCP-compatible client (like Claude Desktop) to search and list high-quality photos.

## Use Cases

### 1. Photo Search
**Goal:** Allow the LLM to find specific images based on user descriptions.
- **Tool:** `search-photos`
- **Parameters:** `query`, `page`, `perPage`, `orderBy`, `collections`, `contentFilter`, `color`, `orientation`, `lang`.
- **Functionality:** Forwards the search request to the Unsplash API and returns a structured JSON response containing photo details, URLs, and metadata.

### 2. Photo Listing
**Goal:** Allow the LLM to browse the latest or popular photos.
- **Tool:** `list-photos`
- **Parameters:** `page`, `perPage`.
- **Functionality:** Retrieves a list of photos from the Unsplash "editorial" or "latest" feed.

### Use Case Diagram

The following mind map illustrates the primary use cases and interactions for the Unsplash Java MCP Server.

```mermaid
mindmap
  root((Unsplash Java MCP Server))
    Search Photos
      ::icon(fa fa-search)
    List Photos
      ::icon(fa fa-list)
```

## System Architecture

The server is built using Java 21 and Spring Boot, leveraging the `spring-ai-mcp` library for protocol implementation.

### Component Diagram

```mermaid
C4Component
    title Component Diagram for Unsplash Java MCP Server

    Person(user, "User", "Wants to find high-quality photos")
    System_Ext(mcp_client, "MCP Client", "Claude Desktop, etc.", "MCP Protocol")
    
    Container_Boundary(spring_boot, "Unsplash Java MCP Server") {
        Component(controller, "UnsplashMcpController", "Spring Controller", "Exposes MCP tools via annotations")
        Component(service, "UnsplashService", "Spring Service", "Handles business logic and API calls")
        Component(rest_client, "RestClient", "Spring RestClient", "Performs HTTP requests to Unsplash")
    }

    System_Ext(unsplash_api, "Unsplash API", "External photo provider", "HTTPS/REST")

    Rel(user, mcp_client, "Asks for photos")
    Rel(mcp_client, controller, "Invokes MCP tools", "SSE")
    
    Rel_D(controller, service, "Calls", "Java")
    Rel_D(service, rest_client, "Uses", "Java")

    Rel_R(rest_client, unsplash_api, "Fetches data", "HTTPS")

    UpdateElementStyle(user, $bgColor="#08427b", $fontColor="white")
    UpdateElementStyle(mcp_client, $bgColor="#01579b", $fontColor="white")
    UpdateElementStyle(spring_boot, $bgColor="none", $fontColor="#000000", $lineColor="#444444")
    UpdateElementStyle(controller, $bgColor="#e1f5fe", $fontColor="#01579b", $lineColor="#01579b")
    UpdateElementStyle(service, $bgColor="#e8f5e9", $fontColor="#1b5e20", $lineColor="#1b5e20")
    UpdateElementStyle(rest_client, $bgColor="#fff3e0", $fontColor="#e65100", $lineColor="#e65100")
    UpdateElementStyle(unsplash_api, $bgColor="#e65100", $fontColor="white")
```

### Sequence Diagram: Tool Execution

The following diagram illustrates how a tool call (e.g., `search-photos`) is processed.

```mermaid
sequenceDiagram
    participant Client as MCP Client
    participant Server as MCP Server (Spring Boot)
    participant Service as UnsplashService
    participant API as Unsplash API

    Client->>Server: Tool Call (search-photos, query="nature")
    Server->>Service: searchPhotos("nature", ...)
    Service->>API: GET /search/photos?query=nature
    API-->>Service: JSON Photo Data
    Service-->>Server: JSON String Result
    Server-->>Client: Tool Result (Content)
```

## Implementation Details

### Technology Stack
- **Framework:** Spring Boot 3.5.9
- **Protocol:** Model Context Protocol (MCP)
- **Library:** `spring-ai-starter-mcp-server-webmvc`
- **HTTP Client:** Spring `RestClient`
- **Language:** Java 21

### Key Components
- **`UnsplashMcpController`**: Defines the MCP tools using `@McpTool` and `@McpToolParam` annotations.
- **`UnsplashService`**: Encapsulates the logic for interacting with the Unsplash REST API.
- **`CorsProperties` & `WebConfig`**: Handles CORS configuration for SSE (Server-Sent Events) transport.

## References & Samples

For practical examples and configuration snippets, refer to:

- **[mcp.jsonl](mcp.jsonl)**: Example configuration for adding this server to an MCP client.
- **[unsplash.http](unsplash.http)**: Sample HTTP requests to test the Unsplash API and the MCP endpoints manually.
- **[README.md](../README.md)**: Main project documentation with setup and running instructions.

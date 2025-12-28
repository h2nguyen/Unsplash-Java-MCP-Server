# Unsplash Java MCP Server

This is a Java-based Model Context Protocol (MCP) server that integrates with the Unsplash API to provide photo search and listing capabilities.

> [!NOTE]
> 
> This project currently provides an initial implementation and does not yet cover the full feature set of the Unsplash API. Additional functionality will be added incrementally over time as new pull requests and feature requests are submitted.

Special thanks to [Unsplash](https://unsplash.com/) for providing the amazing high-quality photos and API used by this project.

## Features

- `searchPhotos`: Search for photos on Unsplash by query.
- `listPhotos`: List photos on Unsplash.

For a more detailed overview of the system design and use cases, see the [Architecture Documentation](docs/ARCHITECTURE.md).

## Getting Started

Follow these steps to quickly get the Unsplash Java MCP Server up and running.

### 1. Prerequisites

- [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/)
- An Unsplash Access Key. You can get one by creating an application on the [Unsplash Developer Portal](https://unsplash.com/developers).

### 2. Clone and Setup

```bash
# Clone the project
git clone https://github.com/h2nguyen/Unsplash-Java-MCP-Server.git
cd Unsplash-Java-MCP-Server

# Create a .env file and add your Unsplash Access Key
echo "UNSPLASH_ACCESS_KEY=your_access_key_here" > .env
```

### 3. Run the Application

The recommended way to run the server for demonstration and testing is using Docker Compose. This starts both the MCP Server and the MCP Inspector.

> [!NOTE]
> 
> The following commands work on Linux and macOS

```bash
# Build and run the application 
docker compose up -d --build
```

```bash
# Stop the application
docker compose down
```

### 4. Verify with MCP Inspector

1.  Open the MCP Inspector UI at [http://localhost:6274](http://localhost:6274).
2.  **Select Transport Type**: Choose `SSE`.
3.  **Enter Server URL**: In the "URL" field, enter `http://localhost:3001/sse`.
4.  **Select Connection Type**: Chose `Direct`.
5.  **Connect**: Click the **Connect** button.
6.  **Explore**: Use **List Tools** and **Call Tool** to test `searchPhotos` and `listPhotos`.

#### Troubleshooting Tips
- **Connection**: If running inside the Docker network from another container, use `http://mcp-server:8080/sse`. From your host machine, use `http://localhost:3001/sse`.
- **Startup**: It may take a few seconds for the Spring Boot application to be ready. Check logs with `docker-compose logs -f`.

## Configuration for Clients (mcp.json)

To use this server with MCP-compatible clients like Claude Desktop, use the following configuration.

### Connecting via SSE (Recommended)

Since this server is built using Spring Boot WebMVC, it uses the **SSE (Server-Sent Events)** transport. Ensure the server is running (e.g., via Docker Compose or `./gradlew bootRun`) before connecting.

```json
{
  "mcpServers": {
    "unsplash": {
      "type": "sse",
      "url": "http://localhost:3001/sse",
      "note": "This MCP server integrates with the Unsplash API. To use it with SSE transport, add this URL directly to the client configuration."
    }
  }
}
```

> [!CAUTION]
> If you are running the server locally without Docker Compose, the default port is `8080`, so the URL would be `http://localhost:8080/sse`.

### Running via Docker

If you prefer to run the server as a standalone Docker container, you can still connect via SSE.

1.  **Build the image**:
    ```shell
    docker build -t unsplash-java-mcp-server .
    ```

2.  **Run the container**:
    ```shell
    docker run -d -p 8080:8080 -e UNSPLASH_ACCESS_KEY=your_access_key_here unsplash-java-mcp-server
    ```

3.  **Configure Client**:
    Use the SSE URL `http://localhost:8080/sse` in your `mcp.json`.

## Development

To run the server locally without Docker:

1.  Ensure you have **Java 21** installed.
2.  Export your key: `export UNSPLASH_ACCESS_KEY=your_access_key_here`
3.  Run: `./gradlew bootRun`

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct and the process for submitting pull requests.

## Reference Documentation

### Unsplash API
- [Unsplash Developer Portal](https://unsplash.com/developers)
- [Unsplash API Documentation](https://unsplash.com/documentation)

### Model Context Protocol (MCP)
- [Official MCP Website](https://modelcontextprotocol.io)
- [MCP Specification](https://modelcontextprotocol.io/specification/)
- [Spring AI MCP Server Documentation](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-server-boot-starter-docs.html)

### Build and Deployment
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.5.9/gradle-plugin)
- [Packaging OCI Images with Spring Boot](https://docs.spring.io/spring-boot/3.5.9/gradle-plugin/packaging-oci-image.html)

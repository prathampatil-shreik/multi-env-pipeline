# Dev Environment

This directory contains documentation and notes for the Dev environment deployment.

## ECS Task Definition Environment Variables

| Variable | Value |
|---|---|
| `APP_ENV` | `DEV` |
| `APP_VERSION` | Injected by pipeline (Git SHA) |
| `APP_MESSAGE` | `Welcome to Development` |

## Notes

- Deployed automatically on every push to `main`.
- Health check: `GET /health`

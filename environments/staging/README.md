# Staging Environment

This directory contains documentation and notes for the Staging environment deployment.

## ECS Task Definition Environment Variables

| Variable | Value |
|---|---|
| `APP_ENV` | `STAGING` |
| `APP_VERSION` | Promoted from Dev (same image tag) |
| `APP_MESSAGE` | `Welcome to Staging` |

## Notes

- Deployed after successful Dev smoke test.
- Health check: `GET /health`
- Same Docker image as Dev — only env vars differ.

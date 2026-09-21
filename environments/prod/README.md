# Production Environment

This directory contains documentation and notes for the Production environment deployment.

## ECS Task Definition Environment Variables

| Variable | Value |
|---|---|
| `APP_ENV` | `PRODUCTION` |
| `APP_VERSION` | Promoted from Staging (same image tag) |
| `APP_MESSAGE` | `Welcome to Production` |

## Notes

- Deployed after successful Staging smoke test.
- Health check: `GET /health`
- Same Docker image as Dev and Staging — only env vars differ.

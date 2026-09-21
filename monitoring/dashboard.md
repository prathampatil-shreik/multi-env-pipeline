# CloudWatch Dashboard

## Metrics to Monitor

| Metric | Description |
|---|---|
| ECS CPU Utilization | Per service, per environment |
| ECS Memory Utilization | Per service, per environment |
| ALB Request Count | Requests per minute |
| ALB Target Response Time | P50, P95, P99 |
| ALB HTTP 5xx Count | Error rate |
| ALB HTTP 4xx Count | Client error rate |

## Health Check

- Endpoint: `GET /health`
- Expected response: `{ "status": "UP" }` with HTTP 200
- ALB health check path: `/health`

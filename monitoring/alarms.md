# CloudWatch Alarms

## Recommended Alarms

| Alarm | Threshold | Action |
|---|---|---|
| ECS CPU > 80% | 5 minutes | SNS notification |
| ECS Memory > 80% | 5 minutes | SNS notification |
| ALB 5xx > 10/min | 1 minute | SNS notification |
| ALB Unhealthy Hosts > 0 | 1 minute | SNS notification |
| ECS Service Task Count < 1 | 1 minute | SNS notification |

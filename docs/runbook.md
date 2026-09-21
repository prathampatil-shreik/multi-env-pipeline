# Runbook

## Deployment Runbook

### Check Application Health

```bash
curl https://<alb-dns>/health
```

### Check Current Version

```bash
curl https://<alb-dns>/version
```

### Rollback

To roll back to a previous version, re-run the pipeline with the previous Git SHA,
or update the ECS service to use the previous image tag from ECR.

```bash
aws ecs update-service \
  --cluster taskflow-cluster-prod \
  --service taskflow-prod \
  --task-definition taskflow-prod:<previous-revision>
```

### View ECS Logs

```bash
aws logs tail /ecs/taskflow-prod --follow
```

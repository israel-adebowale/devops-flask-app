FROM python:3.12-slim

# Create non-root user
RUN useradd -m appuser
WORKDIR /app

# Install deps first (better caching)
COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# Copy source
COPY app.py .

USER appuser
EXPOSE 5000

# Gunicorn for production-like serving
CMD ["gunicorn", "-b", "0.0.0.0:5000", "app:app", "--workers", "2", "--threads", "4"]

from flask import Flask, jsonify
import os
import socket
from datetime import datetime, timezone

app = Flask(__name__)

@app.get("/")
def home():
    return jsonify(
        message="Hello from DevOps Flask Demo",
        status="ok",
        time_utc=datetime.now(timezone.utc).isoformat(),
        hostname=socket.gethostname(),
        version=os.getenv("APP_VERSION", "dev"),
    )

@app.get("/health")
def health():
    return jsonify(status="healthy"), 200

if __name__ == "__main__":
    # Flask dev server is fine for local; in Docker we will run via gunicorn
    app.run(host="0.0.0.0", port=int(os.getenv("PORT", "5000")))

FROM ubuntu:latest
LABEL authors="marbaz"

ENTRYPOINT ["top", "-b"]
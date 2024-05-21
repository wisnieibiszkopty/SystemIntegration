FROM postgres:latest

LABEL authors="Kamil Wodowski Paweł Wiński"

ENV POSTGRES_USER=integration
ENV POSTGRES_PASSWORD=integration
ENV POSTGRES_DB=integration_db

EXPOSE 5432
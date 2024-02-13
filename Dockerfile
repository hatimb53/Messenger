FROM maven:3.9.6-amazoncorretto-17-debian
COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN apt-get update && apt-get install dos2unix && dos2unix /usr/local/bin/entrypoint.sh && chmod +x /usr/local/bin/entrypoint.sh

#Start application
WORKDIR /usr/src/messenger
ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["bash"]
FROM openjdk:17-jdk

WORKDIR /app

COPY /build/libs/discordMusicBot.jar /app

CMD ["java", "-jar", "discordMusicBot.jar"]
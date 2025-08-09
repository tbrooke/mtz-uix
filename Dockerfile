FROM oven/bun:1-alpine as base

WORKDIR /app

# Install Java and Clojure CLI
RUN apk add --no-cache openjdk11-jre curl bash
RUN curl -L -O https://github.com/clojure/brew-install/releases/latest/download/linux-install.sh && \
    chmod +x linux-install.sh && \
    ./linux-install.sh && \
    rm linux-install.sh

FROM base as deps
COPY package.json bun.lockb ./
RUN bun install --frozen-lockfile

FROM base as dev-deps
COPY package.json bun.lockb ./
RUN bun install --frozen-lockfile --dev

FROM dev-deps as build
COPY . .
RUN bun run prod

FROM nginx:alpine as production
COPY --from=build /app/prod /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

FROM dev-deps as development
COPY . .
EXPOSE 3000
CMD ["bun", "dev"]
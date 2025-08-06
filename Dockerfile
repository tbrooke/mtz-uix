FROM oven/bun:1-alpine as base

WORKDIR /app

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
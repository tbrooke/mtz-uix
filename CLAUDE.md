# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a ClojureScript starter project using UIx (React wrapper), TailwindCSS, Shadow-CLJS, and Bun. It creates a React SPA with hot-reloading for both ClojureScript and CSS, producing optimized production builds (~2KB CSS, ~70KB JS gzipped).

## Docker Development Environment

**IMPORTANT: Always work within Docker containers. Never run commands directly on host system.**

### Docker Commands
- `docker-compose up` - Start development environment (available at http://localhost:3000)
- `docker-compose up app-prod --profile production` - Start production build (available at http://localhost:8080)
- `docker-compose exec app <command>` - Execute commands inside development container
- `docker-compose down` - Stop and remove containers

### Development Commands (run inside Docker container)
- `bun dev` - Start development server with hot reload for both CLJS and CSS
- `bun shadow:dev` - Start Shadow-CLJS development build only
- `bun css:dev` - Start TailwindCSS watcher only

### Production Build
- `bun prod` - Create optimized production build with advanced compilation
- `bunx serve prod` - Serve the production build locally

### Individual Build Commands
- `bun shadow:prod` - Compile ClojureScript for production
- `bun css:prod` - Build minified CSS for production
- `bun mkdir:prod` - Copy files to production directory

## Architecture

### Project Structure
- `src/app/` - ClojureScript source files
  - `main.cljs` - Entry point, initializes React root
  - `components.cljs` - Main application components 
  - `ui.cljs` - Reusable UI components with TailwindCSS classes
- `public/` - Static assets and compiled output
- `shadow-cljs.edn` - Shadow-CLJS build configuration
- `deps.edn` - Clojure dependencies (UIx core and dom)
- `tailwind.config.js` - TailwindCSS configuration with different content paths for dev/prod

### Key Libraries
- **UIx 1.1.1** - React wrapper for ClojureScript with hooks support
- **React 18.2.0** - UI library
- **Shadow-CLJS 2.28.16** - ClojureScript build tool
- **TailwindCSS 3.4.13** - Utility-first CSS framework
- **Bun** - JavaScript runtime and package manager

### Component Patterns
- Use `defui` for React functional components
- Use `$` macro for JSX-like syntax with Hiccup-style class vectors
- State management with `use-state` hook
- TailwindCSS classes in vector format: `[text-white bg-blue-500]`
- Merge utility classes with `merge` function for conditional styling

### Build System
- Shadow-CLJS handles ClojureScript compilation with `:browser` target
- TailwindCSS processes styles with JIT compilation
- Production builds use `:advanced` optimization and CSS minification
- Content paths configured differently for development (source files) vs production (compiled JS)

### Development Server
- Runs on http://localhost:3000
- Hot reload for both ClojureScript changes and CSS updates
- Uses `^:dev/after-load` metadata for reload hooks
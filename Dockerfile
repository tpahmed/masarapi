# Use an official Node.js runtime as the base image
FROM node:18-alpine

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application
COPY . .

# Build the application
RUN npm run build

# Serve the application using a lightweight web server
RUN npm install -g serve

# Expose the application port
EXPOSE 3000

# Start the application
CMD ["serve", "-s", "dist", "-l", "3000"]

import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import { resolve } from 'path';

export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      '@': resolve(__dirname, './src'), // Ensure proper path resolution
    },
  },
  define: {
    'process.env': {}, // Optional: For other environment variables
  },
  server: {
    host: true, // Allows access via IP address on your local network
    port: 3000, // Optional: Custom port for the dev server
  },
  build: {
    outDir: 'dist', // Ensures the output directory is consistent
    emptyOutDir: true, // Clears the output directory before building
  },
});

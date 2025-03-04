# Usar una imagen base de Node.js para construir la aplicación
FROM node:18 as build

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar los archivos de la aplicación al contenedor
COPY package*.json ./
COPY . .

# Instalar las dependencias y construir la aplicación
RUN npm install
RUN npm run build --prod

# Usar una imagen base de Nginx para servir la aplicación
FROM nginx:alpine

# Copiar los archivos construidos al directorio de Nginx
COPY --from=build /app/dist/frontend/browser /usr/share/nginx/html

# Exponer el puerto en el que Nginx se ejecutará
EXPOSE 80

# Comando para ejecutar Nginx
CMD ["nginx", "-g", "daemon off;"]
-- Pasajeros
INSERT INTO PASAJERO (rut, nombre, email) VALUES ('111-1', 'Claudia Gomez', 'claudia@mail.com');
INSERT INTO PASAJERO (rut, nombre, email) VALUES ('222-2', 'Pedro Jara', 'pedro@mail.com');
INSERT INTO PASAJERO (rut, nombre, email) VALUES ('333-3', 'Juan Perez', 'juan@mail.com');

-- Hoteles
INSERT INTO HOTEL (id, nombre, ubicacion, capacidad, disponibilidad) VALUES (1, 'Hotel Mar', 'Viña del Mar', 50, 45);
INSERT INTO HOTEL (id, nombre, ubicacion, capacidad, disponibilidad) VALUES (2, 'Hostal Cordillera', 'Santiago', 20, 15);
INSERT INTO HOTEL (id, nombre, ubicacion, capacidad, disponibilidad) VALUES (3, 'Resort Palmas', 'La Serena', 100, 80);

-- Reservas
INSERT INTO RESERVA (id_reserva, id_hotel, rut_pasajero, noches, fecha_inicio, estado, monto_total) VALUES (1, 1, '111-1', 3, TO_DATE('2026-04-20', 'YYYY-MM-DD'), 'Confirmada', 150000);
INSERT INTO RESERVA (id_reserva, id_hotel, rut_pasajero, noches, fecha_inicio, estado, monto_total) VALUES (2, 2, '222-2', 2, TO_DATE('2026-04-21', 'YYYY-MM-DD'), 'Confirmada', 80000);
INSERT INTO RESERVA (id_reserva, id_hotel, rut_pasajero, noches, fecha_inicio, estado, monto_total) VALUES (3, 3, '333-3', 5, TO_DATE('2026-04-22', 'YYYY-MM-DD'), 'Pendiente', 450000);

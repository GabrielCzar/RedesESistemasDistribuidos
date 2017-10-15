#!/usr/bin/python2
import socket

HOST, PORT = "localhost", 9999

TCP_SOCKET = socket.socket(socket.AF_INET, socket.SOCK_STREAM)       
TCP_SOCKET.bind((HOST, PORT))
TCP_SOCKET.listen(2)

listaConectados = []
conectados = 0
print "Conectados: " + str(conectados)

# Max 2 clients connected
while conectados < 2:
    conn, addr = TCP_SOCKET.accept()

    listaConectados.append(conn)

    conectados += 1
    print "Conectados: " + str(conectados)

for c in listaConectados:
    c.send('Conectado!!!')

while True:
    for i in listaConectados:
        print 'conectado: ' , i 

    for c in listaConectados:
        data = c.recv(1024)
        if len(data) > 0:
            for conn in listaConectados:
                if conn != c:
                    conn.send(data)
        

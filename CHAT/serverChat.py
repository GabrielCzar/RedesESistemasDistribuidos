import socket
from threading import Thread

HOST, PORT = "localhost", 9999

TCP_SOCKET = socket.socket(socket.AF_INET, socket.SOCK_STREAM)       
TCP_SOCKET.bind((HOST, PORT))
TCP_SOCKET.listen(2)

listaConectados = []
listaThread = []
conectados = 0

class verificaMensagensClientes(Thread):
    def __init__(self, conn):
        Thread.__init__(self)
        self.conn = conn

    def run(self):
        global listaConectados
        for i in listaConectados:
            print listaConectados
            
        while True:
            data = self.conn.recv(1024)
            for conexao in listaConectados:
                if(conexao != self.conn):
                    conexao.send(data)


while (conectados < 2):
    print "Conectados: " + str(conectados)
    conn, addr = TCP_SOCKET.accept()

    temp = verificaMensagensClientes(conn)
    print temp
    listaThread.append(temp)
    listaConectados.append(conn)

    conectados += 1

while True:
    print "Conectados: " + str(conectados)
    conn, addr = tcp_server_socket.accept()

    temp = verificaMensagensClientes(conn)
    listaThread.append(temp)
    listaConectados.append(conn)
    temp.start()
    
    conectados += 1

for thread in listaThread:
    thread.start()

for conectado in listaConectados:
    conectado.close()


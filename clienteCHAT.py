#!/usr/bin/python
import socket
from threading import Thread

class VerificaMensagens(Thread):
    def __init__(self, tcp):
        Thread.__init__(self)
        self.tcp_cliente_socket = tcp
    def run(self):
        self.tcp_cliente_socket= tcp
        self.fechar= False
        while (not self.fechar):
        #'''Here, the server wait the call clients to send the message to the other clients'''
            data = self.tcp_cliente_socket.recv(1024)
            if (data):
                print data
        print "saiu do laco"
    def fecharVerificaMensagens(self):
        self.fechar = True
        self.tcp_cliente_socket.close()
        

HOST, PORT = "localhost", 9999

tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcp.connect((HOST, PORT))

nome = raw_input( "Digite seu nome: ")
verificaMsg = VerificaMensagens(tcp)
verificaMsg.start()


texto = ""
while texto != "sair":
    texto = raw_input("")
    tcp.send("\n"+nome+": "+ texto)

verificaMsg.fecharVerificaMensagens()
print "finalizado"

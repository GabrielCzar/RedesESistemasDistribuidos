#!/usr/bin/python2
import socket

HOST, PORT = 'localhost', 9999

TPC_SOCKET = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
TPC_SOCKET.connect((HOST, PORT))

nome = raw_input('Digite seu nome: ')

print (TPC_SOCKET.recv(1024))

msg = ''
while True:
    msg = raw_input('Msg: ')
    
    if msg == 'sair':
        break
    TPC_SOCKET.send(nome + ' : ' + msg)
    
    data = TPC_SOCKET.recv(1024)
    print (data)


TPC_SOCKET.send(nome + ' Saiu da conversa!')
TPC_SOCKET.close()

print ("Cliente Finalizado")

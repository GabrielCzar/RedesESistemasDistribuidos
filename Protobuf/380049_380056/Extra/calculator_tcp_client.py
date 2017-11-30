import socket
import calculator_pb2
from decimal import Decimal
import struct 
from google.protobuf.internal import encoder
import google.protobuf.internal.decoder as decoder

def main():
    socket_tcp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    socket_tcp.connect(("localhost", 9999))

    opt = calculator_pb2.Request()
    opt.n1 = float(raw_input("Digite o primeiro numero: "))
    op = raw_input("digite a operacao: ")
    opt.n2 = float(raw_input("Digite o segundo numero: "))
    answer = calculator_pb2.Reply()

    if op == "+":
        opt.op = calculator_pb2.Request.SOM
    elif op == "-" :
        opt.op = calculator_pb2.Request.SUB
    elif op == "*":
        opt.op = calculator_pb2.Request.MUL
    elif op == "/":
        opt.op = calculator_pb2.Request.DIV

    socket_tcp.send(write_delimited_stream(opt))    
    
    answer.ParseFromString(read_delimited_stream(socket_tcp))

    print (answer.res);

def write_delimited_stream(packetMessage):
    serializedMessage = packetMessage.SerializeToString()
    delimiter = encoder._VarintBytes(len(serializedMessage))

    return delimiter + serializedMessage 

def read_delimited_stream(packetMessage):
    data, eddr = packetMessage.recvfrom(1024)
    (size, position) = decoder._DecodeVarint(data, 0)
    return data[position:position + size]

main()



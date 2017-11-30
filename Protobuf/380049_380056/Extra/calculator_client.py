import socket
import calculator_pb2
from decimal import Decimal

def main():
    s = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)

    while True:
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

        s.sendto(opt.SerializeToString(), ("localhost", 9999))
        data, add = s.recvfrom(1024)
        answer.ParseFromString(data)

        print (answer.res);

main()

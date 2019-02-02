import http.server
import socketserver
import datetime
import json

PORT = 8000

#Handler = http.server.SimpleHTTPRequestHandler
class myHandler(http.server.BaseHTTPRequestHandler):
    def do_GET(self):
        now = datetime.datetime.today()
        self.send_response(200)
        self.send_header('Content-type', 'text/plain')
        self.end_headers()
        servertime = now.time()
        formattedtime = servertime.strftime("%H:%M:%S")
        self.wfile.write(bytes(formattedtime, "utf-8"))
        return


with socketserver.TCPServer(("", PORT), myHandler) as httpd:
    print("Serving at port ", PORT)
    httpd.serve_forever()
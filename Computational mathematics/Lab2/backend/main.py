from fastapi import FastAPI, Request, File, UploadFile
from fastapi.responses import HTMLResponse, JSONResponse, PlainTextResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
import json
from backend.sover_equation import solve_equation
from backend.solver_system import sovle_system

last_system = None
last_equation = None

app = FastAPI()

app.mount('/assets', StaticFiles(directory='backend/templates/assets', html=True), name='static')
templates = Jinja2Templates(directory="backend/templates")

@app.get("/Lab2Part1", response_class=HTMLResponse)
@app.get("/Lab2Part2", response_class=HTMLResponse)
@app.get("/Lab3", response_class=HTMLResponse)
@app.get("/Lab4", response_class=HTMLResponse)
@app.get("/Lab5", response_class=HTMLResponse)
@app.get("/Lab6", response_class=HTMLResponse)
@app.get("/", response_class=HTMLResponse)
async def index(request: Request):
    return templates.TemplateResponse("index.html", {"request": request})

@app.get("/solve_equation")
async def index(intervalA, intervalB, method, equalization, accuracy, approach):
    global last_equation
    last_equation = solve_equation(intervalA, intervalB, method, equalization, accuracy, approach)
    return JSONResponse(content=last_equation)

@app.get("/solve_system")
async def index(method, equalization, approachX, approachY, accuracy):
    global last_system
    last_system = sovle_system(approachX, approachY, method, equalization, accuracy)
    return JSONResponse(content=last_system)

@app.get("/download_equation")
async def file_equation():
        print(last_equation)
        return PlainTextResponse(repr(last_equation))

@app.get("/download_system")
async def file_system():
        return PlainTextResponse(repr(last_system))

@app.post("/parse_file_system")
@app.post("/parse_file_equation")
async def parse_file_equation(file: UploadFile = File(...)):
    try:
        contents = file.file.read()
        with open(file.filename, 'wb') as f:
            f.write(contents)
        data = json.loads(contents)
    except Exception:
        return {"message": "Убедитесь, что содержимое файла в формате json"}
    finally:
        file.file.close()
    return JSONResponse(data)
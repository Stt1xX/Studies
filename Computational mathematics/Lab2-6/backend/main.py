from fastapi import FastAPI, Request, File, UploadFile, Query
from fastapi.responses import HTMLResponse, JSONResponse, PlainTextResponse
from fastapi.staticfiles import StaticFiles
from fastapi.templating import Jinja2Templates
import json
from typing import List

from backend.Lab2.solver_equation import solve_equation
from backend.Lab2.solver_system import sovle_system
from backend.Lab3.solver_intergral import solve_intergral
from backend.Lab4.solver_approximation import find_approximation
from backend.Lab5.solver_interpolation import find_interpolation_type1, find_interpolation_type2
from backend.Lab6.differential_solver import solve_differential_equation

last_system = None
last_equation = None
last_integral = None
last_approx = None
last_interpol = None
last_diffeq = None

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

@app.get("/solve/Lab2Part1")
async def index(intervalA, intervalB, method, equalization, accuracy, approach):
    global last_equation
    last_equation = solve_equation(intervalA, intervalB, method, equalization, accuracy, approach)
    return JSONResponse(content=last_equation)

@app.get("/solve/Lab2Part2")
async def index(method, equalization, approachX, approachY, accuracy):
    global last_system
    last_system = sovle_system(approachX, approachY, method, equalization, accuracy)
    return JSONResponse(content=last_system)

@app.get("/solve/Lab3")
async def index(method, equation, left, right, accuracy):
     global last_integral
     last_integral = solve_intergral(method, equation, left, right, accuracy)
     return JSONResponse(content=last_integral)

@app.get("/solve/Lab4")
async def index(x_values : List[str] = Query(None), y_values : List[str] = Query(None)):
     global last_approx
     last_approx = find_approximation(x_values, y_values)
     return JSONResponse(content=last_approx)

@app.get("/solve/Lab5/type1")
async def index(argument, x_values : List[str] = Query(None), y_values : List[str] = Query(None)):
     global last_interpol
     last_interpol = find_interpolation_type1(argument, x_values, y_values)
     return JSONResponse(content=last_interpol)


@app.get("/solve/Lab5/type2")
async def index(left_border, right_border, argument, number_of_equation, number_of_points):
     global last_interpol
     last_interpol = find_interpolation_type2(argument, number_of_equation, left_border, right_border, number_of_points)
     return JSONResponse(content=last_interpol)


@app.get("/solve/Lab6")
async def index(left_border, right_border, accuracy, equation_number, h_value, y0_value):
     global last_diffeq
     last_diffeq = solve_differential_equation(accuracy, y0_value, h_value, left_border, right_border, equation_number)
     return JSONResponse(content=last_diffeq)

@app.get("/download/Lab2Part1")
async def file_equation():
        return PlainTextResponse(str(last_equation))

@app.get("/download/Lab2Part2")
async def file_system():
        return PlainTextResponse(str(last_system))

@app.get("/download/Lab3")
async def file_equation():
        return PlainTextResponse(str(last_integral))

@app.get("/download/Lab4")
async def file_approx():
        return PlainTextResponse(str(last_approx))

@app.get("/download/Lab5")
async def file_interpol():
        return PlainTextResponse(str(last_interpol)) 

@app.get("/download/Lab6")
async def file_diffeq():
        return PlainTextResponse(str(last_diffeq))

@app.post("/parse/Lab2Part1")
@app.post("/parse/Lab2Part2")
@app.post("/parse/Lab4")
@app.post("/parse/Lab5")
@app.post("/parse/Lab6")
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
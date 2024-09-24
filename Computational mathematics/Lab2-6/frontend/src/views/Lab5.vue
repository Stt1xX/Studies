<template>
    <div class="main-wrapper">
        <h1>Lab5</h1>
        <Task value="Пользователю предлагается внести исходные данные тремя способами - в виде набора точек, файлом или выбором функции, с указанием границ и кол-ва точек и выборать точку, в которой требуется вычислить значение функции. Затем посмотреть на результат - найденное значение функции в заданной точке."/>
        <div id="graph-adjustment">
            <div id="adjustment" class="rounded">
                <div id="adjustment-header">                   
                    <div>
                        <button class="arrow" @click="dec_list()">
                            <svg width="64px" height="64px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M6 12H18M6 12L11 7M6 12L11 17" stroke="#6e5b7b" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                        </button>
                        <button class="arrow" @click="inc_list()">
                            <svg width="64px" height="64px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <path d="M6 12H18M18 12L13 7M18 12L13 17" stroke="#6e5b7b" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path> </g></svg>
                        </button>
                    </div>
                    <button type="button" id="file" onclick="$('#file-input').click()">Файл</button>
                    <input type="file" id="file-input" @change="parse_file" style="display: none;" />
                </div>
                <div v-if="current_list == 1">
                    <form>
                        Укажите координаты
                        <div class="custom-input input-wrapper" v-for="i in 12">
                            {{ i }}    
                            <input placeholder="x" v-model="x_values[i-1]" :id="`x${i-1}`" type="text" maxlength="7" @input="check_format(x_inputs[i-1])"/>
                            <input placeholder="y" v-model="y_values[i-1]" :id="`y${i-1}`" type="text" maxlength="7" @input="check_format(y_inputs[i-1])"/>
                        </div>
                        <span style="margin-top: 30px">
                            Укажите аргумент функции
                        </span>
                        <input class="custom-input"v-model="argument_first_list" placeholder="x" id="arg_input_1" type="text" maxlength="7" @input="check_arg_input" />
                        <button  style="margin-top: 15px;"  type="button" id="solve" @click="check_solve">Решить!</button>          
                    </form>
                </div>
                <div v-if="current_list == 2">
                    <form>
                        Выберите уравнение
                        <div class="custom-select">
                            <select id="equation_input" v-model="number_of_equation" @input="check_number_of_equation" >
                                <option value="-1">---</option>
                                <option value="1"> y = 2x - 3</option>
                                <option value="2">y = x&sup3 + 4.81x&sup2 - 17.37x + 5.38</option>
                                <option value="3">y = 5sin(x) - 2cos(x)</option>
                            </select>
                        </div>
                        Укажите промежуток:
                        <div id="interval" class="custom-input">
                            <input id="left_input" v-model="left_border" placeholder="Начало" type="text" maxlength="7" @input="check_left_border"/>
                            <input id="right_input" v-model="right_border" placeholder="Конец" type="text" maxlength="7" @input="check_right_border"/>
                        </div>
                        Укажите количество точек
                        <input class="custom-input" v-model="number_of_points" placeholder="Натуральное число от 2 до 12" type="text" id="number_of_points_input" maxlength="7" @input="check_number_of_points"/>
                        Укажите аргумент функции
                        <input class="custom-input" v-model="argument_second_list" placeholder="x" id="arg_input_2" type="text" maxlength="7" />
                        <button  style="margin-top: 15px;"  type="button" id="solve" @click="check_solve">Решить!</button>      
                    </form>
                </div>
            </div>
            <div id="graph" class="rounded">
                <!-- <Graph :lagrange_points="args_values.lagrange_method.points " :newton_separate_points="args_values.newton_separated_method.points"
                        :newton_finite_points="args_values.newton_finite_method.points"
                        :true_value_points="args_values.true_value.points"
                        :interpol_points="args_values.interpol_points"/>   -->
                <Graph :args_values="args_values"/>
            </div>
        </div>
        <Result :args_values="args_values" :diffs_table="diffs_table"/>
    </div>
</template>

<script setup>

    import Task from '@/components/Task.vue'
    import Result from '@/components/ResultLab5.vue'
    import Graph from '@/components/GraphLab5.vue'
    import { ref, onMounted } from 'vue'
    

    let x_inputs = []
    let y_inputs = []

    onMounted(() => {
        for(let i = 0; i < 12; i++){
            x_inputs.push($('#x' + i))
            y_inputs.push($('#y' + i))
        }
    })

    const current_list = ref(1)
    const number_of_equation = ref(-1)
    const left_border = ref()
    const right_border = ref()
    const number_of_points = ref()
    const argument_first_list = ref()
    

    const x_values = ref(new Array())
    const y_values = ref(new Array())
    const argument_second_list = ref()

    const args_values = ref()
    const diffs_table = ref()

    const dec_list = () => {
        current_list.value = current_list.value % 2 + 1
    }
    const inc_list = () => {
        current_list.value = current_list.value % 2 + 1
    }

    const get_data = () => {
        switch(current_list.value){
            case(1):
                return {
                    "x_values" : x_values.value,
                    "y_values" : y_values.value,
                    "argument" : argument_first_list.value
                }
            case(2):
                return {
                    "number_of_equation" : number_of_equation.value,
                    "left_border" : left_border.value,
                    "right_border" : right_border.value,
                    "argument" : argument_second_list.value,
                    "number_of_points" : number_of_points.value
                }
        }
    }

    const get_url = () => {
        switch(current_list.value){
            case(1):
                return 'solve/Lab5/type1'
            case(2):
                return 'solve/Lab5/type2'
        }
    }

    const ResponseCode = {
        ERROR: 0,
        FIRST_TYPE_RESP: 1,
        SECOND_TYPE_RESP: 2
    }

    const send_data = () => {
        $.ajax({
            url: get_url(),
            method: 'get',
            dataType: 'json',
            data:   get_data(),
            traditional: true,
            success: function(resp){
                if (resp.status != ResponseCode.ERROR){
                    args_values.value = {
                        interpol_points  : {
                            x : resp.interpol_points.x_values,
                            y : resp.interpol_points.y_values
                        },
                        lagrange_method : {
                            header : "Метод Лагранжа:", value : resp.lagrange_result.argument_value, points : {x : resp.lagrange_result.x_values, y : resp.lagrange_result.y_values}
                        },
                        newton_separated_method : {
                            header : "Метод Ньютона (разделенные разности):", value : resp.newton_separated_result.argument_value, points : {x : resp.newton_separated_result.x_values, y : resp.newton_separated_result.y_values}
                        },
                        ...(resp.status == ResponseCode.SECOND_TYPE_RESP ? {
                            true_value : {
                                header : "Истинное значение:", value : resp.true_value.argument_value, points : {x : resp.true_value.x_values, y: resp.true_value.y_values }
                            }
                        } : {
                            true_value : {
                                points : {
                                x : [],
                                y : []
                                }
                            }
                        }),
                        newton_finite_method : {
                            header : "Метод Ньютона (конечные разности):", 
                            ...(resp.newton_finite_result.status == ResponseCode.ERROR? {value : resp.newton_finite_result.error}: 
                            {value : resp.newton_finite_result.argument_value}),
                            ...(resp.newton_finite_result.status != ResponseCode.ERROR) && {points : {x : resp.newton_finite_result.x_values, y : resp.newton_finite_result.y_values}}
                        }
                    }
                    if (resp.newton_finite_result.status == ResponseCode.ERROR){
                        diffs_table.value = undefined
                    } else {
                        diffs_table.value = {header : "Таблица конечных разностей:", table : resp.newton_finite_result.diffs_table}
                    }
                    
                } else {
                    args_values.value = {
                        error_printing : {header : "Ошибка:", value : resp.error}
                   }
                   diffs_table.value = undefined
                }
            }
        })
    }

    const parse_file = () => {
        let formData = new FormData();
		formData.append('file', $("#file-input")[0].files[0]);
        document.getElementById("file-input").value = ''
		$.ajax({
			type: "POST",
			url: '/parse/Lab5',
			cache: false,
			contentType: false,
			processData: false,
			data: formData,
			dataType : 'json',
			success: function(resp){
                switch(resp.adjust_code){
                    case(ResponseCode.FIRST_TYPE_RESP):
                        current_list.value = 1
                        x_values.value = resp.x_values
                        y_values.value = resp.y_values
                        argument_first_list.value = resp.argument
                        break
                    case(ResponseCode.SECOND_TYPE_RESP):
                        current_list.value = 2
                        left_border.value = resp.left_border
                        right_border.value = resp.right_border
                        number_of_points.value = resp.number_of_points
                        argument_second_list.value = resp.argument
                        if ([-1, 1, 2, 3].includes(Number(resp.number_of_equation)))
                            number_of_equation.value = resp.number_of_equation
                        else
                            number_of_equation.value = -1
                        break
                }
			}
		});
    }

    onMounted(() => {
        $('#download').on('click', function(){
            if (args_values.value != null){
                let link = document.createElement('a');
                link.setAttribute('href', '/download/Lab5');
                link.setAttribute('download', 'interpolation.txt');
                link.click();
            }
            return true;
        });
    })

    // Валидация данных 
    const check_format = (input) => {
        if (isNaN(input.val().replace(',', '.'))) {
            input.get(0).setCustomValidity('Некорректный формат данных')
            input.get(0).reportValidity()
            return false;
        }
        input.get(0).setCustomValidity('')
        return true;
    }

    const check_x_y_inputs = () => {
        let counter = 0
        let list_of_x_values = new Set()
        for(let i = 0; i < 12; i++){
            let x_input = $("#x" + i)
            let y_input = $("#y" + i)
            if (x_input.val() == "" && y_input.val() != ""){
                x_input.get(0).setCustomValidity('Пожалуйста заполните координаты полностью')
                x_input.get(0).reportValidity()
                return false
            }
            if (x_input.val() != "" && y_input.val() == ""){
                y_input.get(0).setCustomValidity('Пожалуйста заполните координаты полностью')
                y_input.get(0).reportValidity()
                return false
            }
            if (x_input.val() != "" && y_input.val() != ""){
                counter++
                if (list_of_x_values.has(x_input.val())){
                    x_input.get(0).setCustomValidity('Координата X должна быть уникальной')
                    x_input.get(0).reportValidity()
                    return false
                }
                list_of_x_values.add(x_input.val())
                if (!check_format(x_input) || !check_format(y_input)){
                    return false
                }
            }
        }
        if (counter < 2){
            $('#x0').get(0).setCustomValidity('Пожалуйста заполните координаты хотя бы 2 точки')
            $('#x0').get(0).reportValidity()
            return false
        } else {
            return true
        }
    }

    const check_arg_input = () => {
        let arg_input = $('#arg_input_' + current_list.value)
        if (arg_input.val() == ""){
            arg_input.get(0).setCustomValidity('Обязательно укажите аргумент, в котором мне нужно искать значение')
            arg_input.get(0).reportValidity()
            return false
        } else {
        return check_format(arg_input)
        }
    }

    const check_left_border = () => {
        let left_input = $('#left_input')
        let right_input = $('#right_input')
        if (!check_format(left_input)) {return false}
        else if (Number(left_input.val().replace(',', '.')) >= Number(right_input.val().replace(',', '.'))){
            left_input.get(0).setCustomValidity(`Начало промежутка должно быть меньше его конца`);
            left_input.get(0).reportValidity();
            return false
        } else if (left_input.val() == '') {
            left_input.get(0).setCustomValidity(`Обязательно укажите начало интервала`);
            left_input.get(0).reportValidity();
            return false
        } else{
            left_input.get(0).setCustomValidity("");
            return true
        }
    }

    const check_right_border = () => {
        let left_input = $('#left_input')
        let right_input = $('#right_input')
        if (!check_format(right_input)) {return false}
        else if (Number(left_input.val().replace(',', '.')) >= Number(right_input.val().replace(',', '.'))){
            right_input.get(0).setCustomValidity(`Конец промежутка должен быть больше его начала`);
            right_input.get(0).reportValidity();
            return false
        } else if (right_input.val() == '') {
            right_input.get(0).setCustomValidity(`Обязательно укажите начало интервала`);
            right_input.get(0).reportValidity();
            return false
        } else{
            right_input.get(0).setCustomValidity("");
            return true
        }
    }

    const check_number_of_equation = () => {
        let equation_input = $('#equation_input')
        if (equation_input.val() == -1){
            equation_input.get(0).setCustomValidity(`Обязательно выберите, какую функцию хотите интерполировать`);
            equation_input.get(0).reportValidity();
            return false
        } else {
            equation_input.get(0).setCustomValidity("")
            return true
        }
    }

    const check_number_of_points = () => {
        let number_of_points_input = $('#number_of_points_input')
        if (!check_format(number_of_points_input)) {return false}
        else if (!(isInt(number_of_points_input.val()) && Number(number_of_points_input.val()) >= 2 && Number(number_of_points_input.val()) <= 12)){
            number_of_points_input.get(0).setCustomValidity(`Пожалуйста, проверьте, что число целое и принадлежит промежутку [2, 12]`);
            number_of_points_input.get(0).reportValidity();
            return false
        } else if (number_of_points_input.val() == '') {
            number_of_points_input.get(0).setCustomValidity(`Обязательно укажите количество точек для интерполяции`);
            number_of_points_input.get(0).reportValidity();
            return false
        } else{
            number_of_points_input.get(0).setCustomValidity("");
            return true
        }
    }

    function isInt(value) {
        return !isNaN(value) && 
                parseInt(Number(value)) == value && 
                !isNaN(parseInt(value, 10));
        }

    const check_solve = () => {    
        switch(current_list.value){
            case(1):
                if(check_x_y_inputs() && check_arg_input()){
                    send_data()
                } else {
                    args_values.value = null
                    diffs_table.value = null
                }
                break
            case(2):
                if(check_number_of_equation() && check_left_border() && check_right_border() && check_number_of_points() && check_arg_input()){
                    send_data()
                } else {
                    args_values.value = null
                    diffs_table.value = null
                }
                break
        } 
    }


</script>

<style scoped>

    #format-input{
        display: flex;
        justify-content: center;
        align-content: center;
        flex-direction: column;
    }

    #adjustment-header{
        display: flex;
        justify-content: space-around;
        align-items: center;
        width: 100%;
    }

    #adjustment{    
            text-align: center;
            font-size: 4vh;
            padding: 2vh;
            width: 40%;
    }

    #graph-adjustment {
        display: flex;
        flex-direction: row; 
        justify-content: space-around;
        align-items: center;
    }

    .input-wrapper input {
        padding: 0.675em 0.2em 0.675em 0.2em;
        width: 30%;
        
    }

    .input-wrapper  {
        display: flex;
        justify-content: space-around;
        margin-bottom: 10px;
        width: 50%;
    }

    #adjustment form {
        display: flex;
        justify-content: center;
        flex-wrap: wrap;
    }

    #solve {
        font-size: 3vw;
        padding: 1vh 2vw;
    }

    #file {
        font-size: 1.5vw;
        padding: 1vh 2vw;
    }

    #graph {
        height: 37vw;
        padding: 2vh;
        width: 40%;
    }


    .arrow {
        transition: 0.3s;
        background-color: bisque;
        border-width: 0;
    }

    .arrow:hover {
        transform: scale(1.2);
        cursor: pointer;
        background-color: bisque;
        border-width: 0;
    }

    #solve {
        font-size: 3vw;
        padding: 1vh 2vw;
    }

    #interval {
        justify-content: space-between;
    }
    #interval input {
        width: 40%;
        padding: 0.675em 1em 0.675em 1em;
    }

</style>
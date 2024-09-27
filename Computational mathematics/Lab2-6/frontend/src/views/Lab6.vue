<template>
    <div class="main-wrapper">
        <h1>Lab6</h1>
        <Task value="Пользователю предлагается выбрать дифференциальное уравнение, ввести исходные данные и посмотреть на результат - таблицу приближенных значений интеграла дифференциального уравнения"/>
        <div id="graph-adjustment">
            <div id="adjustment" class="rounded" >
                <div id="selector-input_file" class="custom-select">
                    <div style="width: 60%;">
                        Выберите уравнение
                        <select id="equation_input" v-model="equation_number" @input="check_number_of_equation" style="margin-top: 10px" >
                            <option value="-1">---</option>
                            <option value="1">y' = y + (1 + x)y&sup2</option>
                            <option value="2">y' = x&sup2 + x</option>
                            <option value="3">y' = y + xsin(x)</option>
                        </select>
                    </div>
                    <button type="button" id="file" onclick="$('#file-input').click()">Файл</button>
                    <input type="file" id="file-input" @change="parse_file" style="display: none;" />
                </div>
                <div>
                    <form>
                        Укажите промежуток
                        <div id="interval" class="custom-input">
                            <input id="left_input" v-model="left_border" placeholder="Начало" type="text" maxlength="7" @input="check_left_border"/>
                            <input id="right_input" v-model="right_border" placeholder="Конец" type="text" maxlength="7" @input="check_right_border"/>
                        </div>
                        <div id="step-y0" class="custom-input">
                            <div>
                                <p>Укажите шаг</p>
                                <input id="step_input" v-model="h_value" placeholder="Число должно быть больше 0" type="text" maxlength="7" @input="check_step_value"/>
                            </div>
                            <div>
                                <p>Укажите y<sub>0</sub></p>
                                <input id="y0_input" v-model="y0_value" placeholder="Значение функции в начале промежутка" type="text" maxlength="7" @input="check_y0_value"/>
                            </div>
                        </div>
                        Укажите точность
                        <input id="accuracy_input" v-model="accuracy" placeholder="Укажите число от 0 до 0.1" type="text" maxlength="7" @input="check_accuracy"/>
                        <button  style="margin-top: 15px;"  type="button" id="solve" @click="check_solve">Решить!</button>      
                    </form>
                </div>
            </div>
            <div id="graph" class="rounded">
                <Graph :args_values="data_for_graph"/>
            </div>
        </div>
        <Result :args_values="args_values"/>
    </div>
</template>

<script setup>
    import Task from "@/components/Task.vue"
    import Result from "@/components/ResultLab6.vue"
    import Graph from "@/components/GraphLab6.vue"
    import { ref, onMounted, computed } from 'vue'

    const left_border = ref()
    const right_border = ref()
    const equation_number = ref(-1)
    const h_value = ref()
    const y0_value = ref()
    const accuracy = ref()

    const args_values = ref({})

    const ResponseCode = {
        ERROR: 0,
        OK: 1,
    }

    const data_for_graph = computed(() => {
        if (args_values.value.status == ResponseCode.OK){
            return args_values.value
        }
    })

    // sending data
    const get_data = () => {
        return {
            "left_border" : left_border.value,
            "right_border" : right_border.value,
            "accuracy" : accuracy.value,
            "y0_value" : y0_value.value,
            "h_value" : h_value.value,
            "equation_number" : equation_number.value
        }
    }

    const send_data = () => {
        $.ajax({
            url: '/solve/Lab6',
            method: 'get',
            dataType: 'json',
            data:   get_data(),
            traditional: true,
            success: function(resp){
                switch(Number(resp.status)){
                    case(ResponseCode.ERROR):
                        args_values.value = {
                            status : resp.status,
                            error_message : { header : "Ошибка", value : resp.error }
                        }
                        break
                    case(ResponseCode.OK):
                        args_values.value = {
                            status : resp.status,
                            euler_result : { header : "Метод Эйлера", 
                                            x_values : resp.euler_result.x_values, 
                                            y_values : resp.euler_result.y_values, 
                                            h_value : resp.euler_result.h_value, 
                                            message : resp.euler_result.message,
                                            x_values_noda : resp.euler_result.x_values_noda,
                                            y_values_noda : resp.euler_result.y_values_noda
                            },
                            euler_improved_result : { header : "Метод Эйлера усовершенствованный", 
                                            x_values : resp.euler_improved_result.x_values, 
                                            y_values : resp.euler_improved_result.y_values, 
                                            h_value : resp.euler_improved_result.h_value, 
                                            message : resp.euler_improved_result.message,
                                            x_values_noda : resp.euler_improved_result.x_values_noda,
                                            y_values_noda : resp.euler_improved_result.y_values_noda
                            },
                            miln_result : { header : "Метод Милна", 
                                            x_values : resp.miln_result.x_values, 
                                            y_values : resp.miln_result.y_values,  
                                            message : resp.miln_result.message,
                                            x_values_noda : resp.miln_result.x_values_noda,
                                            y_values_noda : resp.miln_result.y_values_noda
                            },
                            true_result : { header : "Аналитический метод",
                                            x_values : resp.true_result.x_values,
                                            y_values : resp.true_result.y_values,
                                            x_values_noda : resp.true_result.x_values_noda, 
                                            y_values_noda : resp.true_result.y_values_noda,  
                            }
                        }
                        break
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
			url: '/parse/Lab6',
			cache: false,
			contentType: false,
			processData: false,
			data: formData,
			dataType : 'json',
			success: function(resp){
                left_border.value = resp.left_border
                right_border.value = resp.right_border
                accuracy.value = resp.accuracy
                h_value.value = resp.h_value
                y0_value.value = resp.y0_value
                if ([-1, 1, 2, 3].includes(Number(resp.equation_number)))
                    equation_number.value = resp.equation_number
                else
                    equation_number.value = -1     
			}
		});
    }

    onMounted(() => {
        $('#download').on('click', function(){
            if (args_values.value != null){
                let link = document.createElement('a');
                link.setAttribute('href', '/download/Lab6');
                link.setAttribute('download', 'Lab6_result.txt');
                link.click();
            }
            return true;
        });
    })


    // Валидацция данных


    const check_number_of_equation = () => {
        let equation_input = $('#equation_input')
        if (equation_input.val() == -1){
            equation_input.get(0).setCustomValidity(`Обязательно выберите, какой диффур хотите решить`);
            equation_input.get(0).reportValidity();
            return false
        } else {
            equation_input.get(0).setCustomValidity("")
            return true
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

    const check_accuracy = () => {
        let accuracy_input = $('#accuracy_input')
        if (!check_format(accuracy_input)) {return false}
        else if (Number(accuracy_input.val()) > 0.1){
            accuracy_input.get(0).setCustomValidity(`Точность слишком большая`);
            accuracy_input.get(0).reportValidity();
            return false
        } else if (accuracy_input.val() == ""){
            accuracy_input.get(0).setCustomValidity(`Обязательно укажите точность`);
            accuracy_input.get(0).reportValidity();
            return false
        } else if (Number(accuracy_input.val()) <= 0){
            accuracy_input.get(0).setCustomValidity(`Точность должна быть положительной`);
            accuracy_input.get(0).reportValidity();
            return false
        } else {
            accuracy_input.get(0).setCustomValidity("")
            return true
        }
    }

    const check_y0_value = () => {
        let y0_input = $('#y0_input')
        if (!check_format(y0_input)) {return false}
        else if (y0_input.val() == ""){
            y0_input.get(0).setCustomValidity(`Обязательно укажите начальное условие`);
            y0_input.get(0).reportValidity();
            return false
        } else {
            y0_input.get(0).setCustomValidity("")
            return true
        }
    }

    const check_step_value = () => {
        let step_input = $('#step_input')
        let left_input = $('#left_input')
        let right_input = $('#right_input')
        if (!check_format(step_input)) {return false}
        else if (step_input.val() == ""){
            step_input.get(0).setCustomValidity(`Обязательно укажите шаг`);
            step_input.get(0).reportValidity();
            return false
        } else if (Number(step_input.val()) <= 0){
            step_input.get(0).setCustomValidity(`Шаг должен быть положительным`);
            step_input.get(0).reportValidity();
            return false
        } else if (Number(step_input.val()) > Number(right_input.val()) - Number(left_input.val())){
            step_input.get(0).setCustomValidity(`Шаг не должен быть больше промежутка`);
            step_input.get(0).reportValidity();
            return false
        } else {
            step_input.get(0).setCustomValidity("")
            return true
        }
    }

    const check_format = (input) => {
        if (isNaN(input.val().replace(',', '.'))) {
            input.get(0).setCustomValidity('Некорректный формат данных')
            input.get(0).reportValidity()
            return false;
        }
        input.get(0).setCustomValidity('')
        return true;
    }

    const check_solve = () => {    
        if(check_number_of_equation() && check_left_border() 
        && check_right_border() && check_step_value() 
        && check_y0_value() && check_accuracy()){
            send_data()
        }
    }


</script>

<style scoped>

    #interval, #step-y0 {
        justify-content: space-between;
    }

    #interval input {
        width: 40%;
        padding: 0.675em 1em 0.675em 1em;
    }

    #step-y0 div {
        width: 40%;
    }

    #step-y0 div p {
        margin: 0 0 10px 0
    }

    #file {
        font-size: 1.5vw;
        padding: 1vh 2vw;
    }

    #solve {
        font-size: 3vw;
        padding: 1vh 2vw;
    }

    #adjustment{
        text-align: center;
        font-size: 3vh;
        padding: 2vh;
        width: 40%;
        margin-bottom: 20px;
    }

    #graph-adjustment {
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        justify-content: space-around;
        width: 100%;
    }

    #graph {
        padding: 2vh;
        margin-bottom: 20px;
        width: 40%;
        height: 37vw;
    }

    #my-slider {
        margin-top: 3vh;
        width: 70%;
    }

    .slider-input {
        width: 40%;
    }

    #download {
        font-size: 1vw;
        padding: 0.5vh 1vw;
    }
</style>
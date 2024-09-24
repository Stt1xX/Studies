<template>
    <div class="main-wrapper">
        <h1>Lab4</h1>
        <Task value="Пользователю предлагается ввести координаты 8-12 точек и посмотреть на результат - найденные апроксимирующие эмпирические функции"/>
        <div id="graph-adjustment">
            <div id="adjustment" class="rounded">
                <form>
                    <div style="margin-bottom: 15px;">
                        Укажите координаты
                        <button type="button" id="file" onclick="$('#file-input').click()">Файл</button>
                        <input type="file" id="file-input" @change="parse_file" style="display: none;" />
                    </div>
                    <div class="custom-input input-wrapper">
                        1
                        <input id="x1" v-model="x_values[0]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[0].x)"/>
                        <input id="y1" v-model="y_values[0]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[0].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        2
                        <input id="x2" v-model="x_values[1]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[1].x)"/>
                        <input id="y2" v-model="y_values[1]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[1].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        3
                        <input id="x3" v-model="x_values[2]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[2].x)"/>
                        <input id="y3" v-model="y_values[2]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[2].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        4
                        <input id="x4" v-model="x_values[3]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[3].x)"/>
                        <input id="y4" v-model="y_values[3]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[3].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        5
                        <input id="x5" v-model="x_values[4]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[4].x)"/>
                        <input id="y5" v-model="y_values[4]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[4].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        6
                        <input id="x6" v-model="x_values[5]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[5].x)"/>
                        <input id="y6" v-model="y_values[5]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[5].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        7
                        <input id="x7" v-model="x_values[6]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[6].x)"/>
                        <input id="y7" v-model="y_values[6]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[6].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        8
                        <input id="x8" v-model="x_values[7]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[7].x)"/>
                        <input id="y8" v-model="y_values[7]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[7].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        9
                        <input id="x9" v-model="x_values[8]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[8].x)"/>
                        <input id="y9" v-model="y_values[8]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[8].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        10
                        <input id="x10" v-model="x_values[9]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[9].x)"/>
                        <input id="y10" v-model="y_values[9]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[9].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        11
                        <input id="x11" v-model="x_values[10]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[10].x)"/>
                        <input id="y11" v-model="y_values[10]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[10].y)"/>
                    </div>
                    <div class="custom-input input-wrapper">
                        12
                        <input id="x12" v-model="x_values[11]" placeholder="x" type="text" maxlength="7" @input="check_format(all_inputs[11].x)"/>
                        <input id="y12" v-model="y_values[11]" placeholder="y" type="text" maxlength="7" @input="check_format(all_inputs[11].y)"/>
                    </div>
                    <button  style="margin-top: 15px;"  type="button" id="solve" @click="check_solve">Решить!</button>
                </form>
            </div>
            <div id="graph" class="rounded">
                <Graph :x_values="x_values" :y_values="y_values" :current_list="current_list" 
                :linear="linear_response" :general="general_response" :quadratic="quadratic_response" 
                :cubic="cubic_response" :degree="degree_response" :exp="exp_response" :log="log_response"/>
            </div>         
        </div>
    <Result @change-list="change_current_list" :linear="linear_response" :general="general_response" :quadratic="quadratic_response" 
    :cubic="cubic_response" :degree="degree_response" :exp="exp_response" :log="log_response" />
    </div>

</template>

<script setup>

    import {ref, onMounted} from 'vue'

    import Task from '../components/Task.vue'
    import Result from '@/components/ResultLab4.vue'
    import Graph from '@/components/GraphLab4.vue'

    const general_response = ref()
    const linear_response = ref()
    const quadratic_response = ref()
    const cubic_response = ref()
    const degree_response = ref()
    const exp_response = ref() 
    const log_response = ref()

    const x_values = ref(new Array(12))
    const y_values = ref(new Array(12))

    const current_list = ref(1)

    /** Валидация **/
    let all_inputs = null

    onMounted(() => {
        all_inputs = [
            {x: $("#x1"), y: $("#y1")}, {x: $("#x2"), y: $("#y2")},
            {x: $('#x3'), y: $('#y3')}, {x: $('#x4'), y: $('#y4')}, 
            {x: $('#x5'), y: $('#y5')}, {x: $('#x6'), y: $('#y6')}, 
            {x: $('#x7'), y: $('#y7')}, {x: $('#x8'), y: $('#y8')}, 
            {x: $('#x9'), y: $('#y9')}, {x: $('#x10'), y: $('#y10')}, 
            {x: $('#x11'), y: $('#y11')}, {x: $('#x12'), y: $('#y12')}]
    })

    

    const check_format = (input) => {
        if (isNaN(input.val().replace(',', '.'))) {
            input.get(0).setCustomValidity('Некорректный формат данных')
            input.get(0).reportValidity()
            return false;
        }
        input.get(0).setCustomValidity('')
        return true;
    }

    const check_all_inputs = () => {
        let counter = 0
        let list_of_x_values = new Set()
        for(const input of all_inputs){
            if (input.x.val() == "" && input.y.val() != ""){
                input.x.get(0).setCustomValidity('Пожалуйста заполните координаты полностью')
                input.x.get(0).reportValidity()
                return false
            }
            if (input.x.val() != "" && input.y.val() == ""){
                input.y.get(0).setCustomValidity('Пожалуйста заполните координаты полностью')
                input.y.get(0).reportValidity()
                return false
            }
            if (input.x.val() != "" && input.y.val() != ""){
                counter++
                if (list_of_x_values.has(input.x.val())){
                    input.x.get(0).setCustomValidity('Координата X должна быть уникальной')
                    input.x.get(0).reportValidity()
                    return false
                }
                list_of_x_values.add(input.x.val())
                if (!check_format(input.x) || !check_format(input.y)){
                    return false
                }
            }
        }
        if (counter < 8){
            $('#x1').get(0).setCustomValidity('Пожалуйста заполните координаты хотя бы 8 точек')
            $('#x1').get(0).reportValidity()
            return false
        } else {
            return true
        }
    } 

    const check_solve = () => {     
        if(check_all_inputs()){
        send_data()
        } else {
            linear_response.value = null
            general_response.value = null
        }
    }

    const send_data = () => {
        $.ajax({
            url: '/solve/Lab4',
            method: 'get',
            dataType: 'json',
            data: {
                "x_values" : x_values.value,
                "y_values" : y_values.value
            }, 
            traditional: true,
            success: function(resp){
                if (resp.status == 1){
                    general_response.value = {
                        "x_values" : resp.x_values, "y_values" : resp.y_values
                    }
                    linear_response.value = {
                        "values" : resp.linear_result.values, "differences" : resp.linear_result.differences,
                        "deviation" : resp.linear_result.deviation, "a" : resp.linear_result.a, "b" : resp.linear_result.b,
                        "pirson" : resp.linear_result.pirson, "reliability" : resp.linear_result.reliability, 
                        "status" : resp.linear_result.status
                    }
                    quadratic_response.value = {
                        "values" : resp.quadratic_result.values, "differences" : resp.quadratic_result.differences,
                        "deviation" : resp.quadratic_result.deviation, "a" : resp.quadratic_result.a, "b" : resp.quadratic_result.b,
                        "c" : resp.quadratic_result.c, "reliability" : resp.quadratic_result.reliability, 
                        "status" : resp.quadratic_result.status
                    }
                    cubic_response.value = {
                        "values" : resp.cubic_result.values, "differences" : resp.cubic_result.differences,
                        "deviation" : resp.cubic_result.deviation, "a" : resp.cubic_result.a, "b" : resp.cubic_result.b,
                        "c" : resp.cubic_result.c, "d" : resp.cubic_result.d, "reliability" : resp.cubic_result.reliability,
                        "status" : resp.cubic_result.status
                    }
                    degree_response.value = {
                        "values" : resp.degree_result.values, "differences" : resp.degree_result.differences,
                        "deviation" : resp.degree_result.deviation, "a" : resp.degree_result.a, "b" : resp.degree_result.b,
                        "reliability" : resp.degree_result.reliability, "status" : resp.degree_result.status,
                        "error" : resp.degree_result.error
                    }
                    exp_response.value = {
                        "values" : resp.exp_result.values, "differences" : resp.exp_result.differences,
                        "deviation" : resp.exp_result.deviation, "a" : resp.exp_result.a, "b" : resp.exp_result.b,
                        "reliability" : resp.exp_result.reliability, "status" : resp.exp_result.status,
                        "error" : resp.exp_result.error
                    }
                    log_response.value = {
                        "values" : resp.log_result.values, "differences" : resp.log_result.differences,
                        "deviation" : resp.log_result.deviation, "a" : resp.log_result.a, "b" : resp.log_result.b,
                        "reliability" : resp.log_result.reliability, "status" : resp.log_result.status,
                        "error" : resp.log_result.error
                    }
                }
                // else {    
                //     linear_response.value = {'Ошибка' : resp.error}
                // }
            }
        })
    }


    const parse_file = () => {
        let formData = new FormData();
		formData.append('file', $("#file-input")[0].files[0]);
        document.getElementById("file-input").value = ''
		$.ajax({
			type: "POST",
			url: '/parse/Lab4',
			cache: false,
			contentType: false,
			processData: false,
			data: formData,
			dataType : 'json',
			success: function(response){
                if (response.x_values != null){
                    x_values.value = response.x_values
                }
                if (response.y_values != null){
                    y_values.value = response.y_values
                }
			}
		});
    }

    onMounted(() => {
        $('#download').on('click', function(){
            if (general_response.value != null){
                let link = document.createElement('a');
                link.setAttribute('href', '/download/Lab4');
                link.setAttribute('download', 'temp_approx.txt');
                link.click();
            }
            return true;
        });
    })


    const change_current_list = (arg) => {
        current_list.value = arg
    }

</script>

<style scoped>

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

</style>
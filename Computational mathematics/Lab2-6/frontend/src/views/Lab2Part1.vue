<template>
    <div class="main-wrapper">
        <h1>Lab2.1</h1>
        <Task value="Пользователю предлагается выбрать уравнение, выбрать метод решения, ввести исходные данные и посмотреть на результат - найденный на заданном промежутке корень уравнения"/>
        <div id="graph-adjustment">
            <div id="adjustment" class="rounded">
                <form>
                    <div id="methods-file" class="custom-select">
                        <div style="width: 60%;">
                            Выберите метод:
                            <select id="method" v-model="method" style="margin-top: 10px;">
                                <option value="-1">---</option>
                                <option value="1">Половинного деления</option>
                                <option value="2">Секущих</option>
                                <option value="3">Простых итераций</option>
                            </select>
                        </div>
                        <button type="button" id="file" onclick="$('#file-input').click()">Файл</button>
                        <input type="file" id="file-input" @change="parse_file" style="display: none;" />
                    </div>
                    Выберите уравнение:
                    <div class="custom-select">
                        <select id="equalization" v-model="equalization">
                            <option value="-1">---</option>
                            <option value="1"> y = 2,74x&sup3 - 1,93x&sup2 - 15,28x - 3,72</option>
                            <option value="2">y = -1,38x&sup3 - 5,42x&sup2 + 2,57x + 10,95</option>
                            <option value="3">y = x&sup3 + 2,84x&sup2 - 5,606x - 14,766</option>
                            <option value="4">y = sin(x + 1) - 0,2</option>
                        </select>
                    </div>
                    Укажите промежуток:
                    <div id="interval" class="custom-input">
                        <input id="interval-a" placeholder="Начало" type="text" maxlength="7" @input="check_intervalA"/>
                        <input id="interval-b" placeholder="Конец" type="text" maxlength="7" @input="check_intervalB"/>
                    </div>
                    Укажите точность:
                    <div class="custom-input">
                        <input id="accuracy" placeholder="Укажите число от 0 до 0.1" type="text" maxlength="15" @input="check_accuracy" />
                    </div>
                    Укажите начальное приближение (не обязательно):
                    <div class="custom-input">
                        <input id="approach" placeholder="Укажите число внутри промежутка" type="text" maxlength="7" @input="check_approach"/>
                    </div>
                    <button type="button" id="solve" @click="check_solve">Решить!</button>
                </form>
            </div>
            <div id="graph-height">
                <div id="graph" class="rounded">
                    <Graph :equation_system="-1" :equation="equalization" :left="left" :right="right" :bottom="height[0]" :top="height[1]"/>
                </div>
                <div id="height-adjustment" class="rounded">
                    <span style="width: 100%; text-align: center; margin-bottom: 1vh;">Высота:</span>
                    <input class="slider-input" v-model="height[0]" id="bottom" type="text" maxlength="7" @input="check_bottom" />
                    <input class="slider-input"  v-model="height[1]" id="top" type="text" maxlength="7" @input="check_top"/>
                    <Slider id="my-slider" v-model="height" :range=true :min=-1000 :max=1000 />
                </div>
            </div>            
        </div>
        <Result :value="response" />
    </div>
</template>

<script setup>
    import Task from '../components/Task.vue';
    import Result from '../components/Result.vue';
    import {onMounted, ref} from 'vue';
    import Graph from '../components/GraphLab2.vue';
    import Slider from 'primevue/slider';
    
    const method = ref(-1)
    const equalization = ref(-1)
    const left = ref(0)
    const right = ref(10)
    const accuracy = ref()
    const approach = ref()


    let height = ref([-30, 30])

    const response = ref()

    /** Запросы **/

    const sent_data = () => {
        $.get('/solve/Lab2Part1', {
            method: method.value,
            equalization: equalization.value,
            intervalA: left.value,
            intervalB: right.value,
            accuracy: accuracy.value,
            approach: approach.value
        }, function(resp){
            if (resp.status == 1){
                response.value = {'Корень:' : resp.root, 'Значение функции:' : resp.value, 'Количество итераций:' : resp.number_of_iterations}
            } else {
                response.value = {'Ошибка' : resp.error}
            }
        });
    }

    const parse_file = () => {
        let formData = new FormData();
		formData.append('file', $("#file-input")[0].files[0]);
        document.getElementById("file-input").value = ''
		$.ajax({
			type: "POST",
			url: '/parse/Lab2Part1',
			cache: false,
			contentType: false,
			processData: false,
			data: formData,
			dataType : 'json',
			success: function(response){
                if (response.left != null){
                    left.value = response.left
                    left_form.value = response.left
                }
                if (response.right != null){
                    right.value = response.right
                    right_form.value = response.right
                }
                if (response.accuracy != null){
                    accuracy.value = response.accuracy
                    accuracy_form.value = response.accuracy
                }
                if (response.approach != null){
                    approach.value = response.approach
                    approach_form.value = response.approach
                }
                if (equalization_form.querySelector('[value="' + response.equalization + '"]') != null){
                    equalization.value = response.equalization
                }
                if (method_form.querySelector('[value="' + response.method + '"]') != null){
                    method.value = response.method
                }
			}
		});
    }

    onMounted(() => {
        $('#download').on('click', function(){
            if (response.value != null){
                let link = document.createElement('a');
                link.setAttribute('href', '/download/Lab2Part1');
                link.setAttribute('download', 'temp_equation.txt');
                link.click();
            }
            return true;
        });
    })

    /** Валидация **/
    let left_form = null
    let right_form = null
    let accuracy_form = null
    let approach_form = null
    let equalization_form = null
    let method_form = null
    let bottom_form = null
    let top_form = null

    onMounted(() => {
        left_form = document.getElementById('interval-a')
        right_form = document.getElementById('interval-b')
        accuracy_form = document.getElementById('accuracy')
        approach_form = document.getElementById('approach')
        equalization_form = document.getElementById('equalization')
        method_form = document.getElementById('method')
        bottom_form = document.getElementById('bottom')
        top_form = document.getElementById('top')
    })

    const check_intervalA = () => {
        left.value = left_form.value.replace(',', '.')
        if (!check_format(left_form)) {return false}
        else if (Number(left.value) >= Number(right.value)){
            left_form.setCustomValidity(`Начало промежутка должно быть меньше его конца`);
            left_form.reportValidity();
            return false
        } else if (left_form.value == '') {
            left_form.setCustomValidity(`Обязательно укажите начало интервала`);
            left_form.reportValidity();
            return false
        } else{
            left_form.setCustomValidity("");
            right_form.setCustomValidity("");
            return true
        }
    }

    const check_intervalB = () => {
        right.value = right_form.value.replace(',', '.')
        if (!check_format(right_form)) {return false}
        else if (Number(left.value) >= Number(right.value)){
            right_form.setCustomValidity(`Начало промежутка должно быть меньше его конца`);
            right_form.reportValidity();
            return false
        } else if (right_form.value == '') {
            right_form.setCustomValidity(`Обязательно укажите конец интервала`);
            right_form.reportValidity()
            return false
        } else{
            left_form.setCustomValidity("");
            right_form.setCustomValidity("");
            return true
        }
    }

    const check_bottom = () => {
        height.value[0] = bottom_form.value.replace(',', '.')
        if (!check_format(bottom_form)) {}
        else if (Number(height.value[0]) < -1000){
            height.value[0] = -1000
        } else if (Number(height.value[0]) > 1000){
            height.value[0] = 1000
        } else if (Number(height.value[0]) >= Number(height.value[1])){
            bottom_form.setCustomValidity(`Начало промежутка должно быть меньше его конца`);
            bottom_form.reportValidity();
        } else if (bottom_form.value == '') {
            bottom_form.setCustomValidity(`Обязательно укажите конец интервала`);
            bottom_form.reportValidity();
        } else{
            bottom_form.setCustomValidity("");
            top_form.setCustomValidity("");
        }
    }

    const check_top = () => {
        height.value[1] = top_form.value.replace(',', '.')
        if (!check_format(top_form)) {}
        else if (Number(height.value[1]) > 1000){
            height.value[1] = 1000
        } else if (Number(height.value[1]) < -1000){
            height.value[1] = -1000
        } else if (Number(height.value[0]) >= Number(height.value[1])){
            top_form.setCustomValidity(`Начало промежутка должно быть меньше его конца`);
            top_form.reportValidity();
        } else if (top_form.value == '') {
            top_form.setCustomValidity(`Обязательно укажите конец интервала`);
            top_form.reportValidity();
        } else{
            bottom_form.setCustomValidity("");
            top_form.setCustomValidity("");
        }
    }

    const check_accuracy = () => {
        accuracy.value = accuracy_form.value.replace(',', '.')
        if (!check_format(accuracy_form)) {return false}
        else if (Number(accuracy.value) > 0.1){
            accuracy_form.setCustomValidity(`Точность слишком большая`);
            accuracy_form.reportValidity();
            return false
        } else if (accuracy_form.value == ""){
            accuracy_form.setCustomValidity(`Обязательно укажите точность`);
            accuracy_form.reportValidity();
            return false
        } else if (Number(accuracy.value) <= 0){
            accuracy_form.setCustomValidity(`Точность должна быть положительной`);
            accuracy_form.reportValidity();
            return false
        } else {
            accuracy_form.setCustomValidity("")
            return true
        }
    }

    const check_approach = () => {
        approach.value = approach_form.value.replace(',', '.')
        if (!check_format(approach_form)) {return false}
        else if (approach_form.value == "") {return true}
        else if (Number(approach.value) < Number(left.value) || Number(approach.value) > Number(right.value)){
            approach_form.setCustomValidity(`Приближение не может быть вне указанного промежутка`);
            approach_form.reportValidity();
            return false
        } else {
            approach_form.setCustomValidity("")
            return true
        }
    }

    const check_equalization = () => {
        if (equalization.value == -1){
            equalization_form.setCustomValidity(`Обязательно выберите, какое уравнение хотите решить`);
            equalization_form.reportValidity();
            return false
        } else {
            equalization_form.setCustomValidity("")
            return true
        }
    }

    const check_method = () => {
        if (method.value == -1){
            method_form.setCustomValidity(`Обязательно выберите, каким методом хотите решать`);
            method_form.reportValidity();
            return false
        } else {
            method_form.setCustomValidity("")
            return true
        }
    }

    const check_format = (input) => {
        if (isNaN(input.value.replace(',', '.'))) {
            input.setCustomValidity('Некорректный формат данных')
            input.reportValidity()
            return false;
        }
        return true;
    }

    const check_solve = () => {     
        if(
        check_method() &&
        check_equalization() &&
        check_intervalA() &&
        check_intervalB() &&
        check_accuracy() &&
        check_approach()        
        ){
        sent_data()
        } else {
            response.value = null
        }
    }   
</script>

<style scoped>

    #my-slider {
        margin-top: 3vh;
        width: 70%;
    }

    .slider-input {
        width: 40%;
    }

    #height-adjustment {
        padding: 2vh;
        display: flex;
        justify-content: space-around;
        align-items: center;
        height: 20%;
        flex-wrap: wrap;
    }

    #graph-adjustment {
        display: flex;
        flex-direction: row; 
        justify-content: space-around;
    }

    #adjustment{
        text-align: center;
        font-size: 3vh;
        padding: 2vh;
        width: 40%;
    }

    #graph-height {
        display: flex;
        flex-direction: column;
        justify-content: space-around;
        width: 40%;
        font-size: 3vh;
    }

    #graph {
        height: 37vw;
        padding: 2vh;
        margin-bottom: 20px;
    }

    #interval {
        justify-content: space-between;
    }
    #interval input {
        width: 40%;
        padding: 0.675em 1em 0.675em 1em;
    }

    #file {
        font-size: 1.5vw;
        padding: 1vh 2vw;
    }

    #solve {
        font-size: 3vw;
        padding: 1vh 2vw;
    }



</style>
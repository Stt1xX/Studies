<template>
    <div class="main-wrapper">
        <h1>Lab2.2</h1>
        <Task value="Пользователю предлагается выбрать систему уравнений, выбрать метод решения, ввести исходные данные и посмотреть на результат - найденное на указанном промежутке пересечение графиков"/>
        <div id="graph-adjustment">
            <div id="adjustment" class="rounded">
                <form>
                    <div id="methods-file" class="custom-select">
                        <div style="width: 60%;">
                            Выберите метод:
                            <select id="method" v-model="method" style="margin-top: 10px;">
                                <option value="-1">---</option>
                                <option value="1">Метод Ньютона</option>
                            </select>
                        </div>
                        <button type="button" id="file" onclick="$('#file-input').click()">Файл</button>
                        <input type="file" id="file-input" @change="parse_file" style="display: none;" />
                    </div>
                    Выберите систему уравнений:
                    <div class="custom-select">
                        <select id="equalization" v-model="equalization">
                            <option value="-1">---</option>
                            <option value="1">sin(x + 1) - y = 1,2 |########| -2y + cos(x) = 2</option>
                            <option value="2">cos(x - 1) + y = 0,5 |########|  x&sup2 + y&sup2 = 2</option>
                        </select>
                    </div>
                    Укажите начальное приближение:
                    <div id="approach" class="custom-input">
                        <input id="approach-x" placeholder="x" type="text" maxlength="7" @input="check_approachX"/>
                        <input id="approach-y" placeholder="y" type="text" maxlength="7" @input="check_approachY"/>
                    </div>
                    Укажите точность:
                    <div class="custom-input">
                        <input id="accuracy" placeholder="Укажите число от 0 до 0.1" type="text" maxlength="15" @input="check_accuracy" />
                    </div>
                    <button type="button" id="solve" @click="check_solve">Решить!</button>
                </form>
            </div>
            <div id="graph" class="rounded">
                <Graph :equation_system="equalization" :equation="-1" :left="Number(approachX) - Number(radius[0])  " :right="Number(approachX) + Number(radius[0])" :bottom="Number(approachY) - Number(radius[1])" :top="Number(approachY) + Number(radius[1])"/>
            </div>
            <div id="result" class="rounded">
                <div>Результат:</div>
                <button type="button" id="download">Скачать</button>
                <p v-for="(value, key) in response"> {{ key }}<br> {{ value }} </p>
        </div>
            <div id="radius-adjustment" class="rounded">
                <span style="width: 100%; text-align: center; margin-bottom: 1vh;">Радиус:</span>
                <input class="slider-input" id="left" type="text" maxlength="7" v-model="radius[0]" @input="check_radius_low" />
                <input class="slider-input" id="right" type="text" maxlength="7" v-model="radius[1]" @input="check_radius_high"/>
                <Slider id="my-slider" v-model="radius" :range=true :min=0 :max=100 />
            </div>
        </div>
    </div>
</template>

<script setup>
    import Task from '../components/Task.vue'
    import Graph from '../components/GraphLab2.vue';
    import Slider from 'primevue/slider';
    import { ref, onMounted } from 'vue';

    const method = ref(-1)
    const equalization = ref(-1)
    const approachX = ref(0)
    const approachY = ref(0)
    const response = ref()
    const accuracy = ref()
    let radius = ref([3, 3])

    /** Запросы **/

    const sent_data = () => {
        $.get('/solve/Lab2Part2', {
            method: method.value,
            equalization: equalization.value,
            approachX: approachX.value,
            approachY: approachY.value,
            accuracy: accuracy.value
        }, function(resp){
            if (resp.status == 1){
                response.value = {'Корень:' : resp.roots, 'Вектор погрешностей:' : resp.vector_of_inaccuracy, 'Количество итераций:' : resp.number_of_iterations}
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
			url: '/parse/Lab2Part2',
			cache: false,
			contentType: false,
			processData: false,
			data: formData,
			dataType : 'json',
			success: function(response){
                if (response.accuracy != null){
                    accuracy.value = response.accuracy
                    accuracy_form.value = response.accuracy
                }
                if (response.approachX != null){
                    approachX.value = response.approachX
                    approachX_form.value = response.approachX
                }
                if (response.approachY != null){    
                    approachY.value = response.approachY
                    approachY_form.value = response.approachY
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
                link.setAttribute('href', '/download/Lab2Part2');
                link.setAttribute('download', 'temp_system.txt');
                link.click();
            }
            return true;
        });
    })

    /** Валидация **/
    let equalization_form = null
    let method_form = null
    let accuracy_form = null
    let approachX_form = null
    let approachY_form = null
    let low_radius_form = null
    let high_radius_form = null

    onMounted(() => {
        equalization_form = document.getElementById('equalization')
        method_form = document.getElementById('method')
        accuracy_form = document.getElementById('accuracy')
        approachX_form = document.getElementById('approach-x')
        approachY_form = document.getElementById('approach-y')
        low_radius_form = document.getElementById('left')
        high_radius_form = document.getElementById('right')
    })

    const check_approachX = () => {
        approachX.value = approachX_form.value.replace(',', '.')
        if (!check_format(approachX_form)) {
            return false
        }
        else if (approachX_form.value == "") {
            approachX_form.setCustomValidity(`Обязательно укажите приближение по оси X`);
            approachX_form.reportValidity();
            return false
        } else{
            approachX_form.setCustomValidity("");
            return true
        }
    }

    const check_approachY = () => {
        approachY.value = approachY_form.value.replace(',', '.')
        if (!check_format(approachY_form)) {return false}
         else if (approachY_form.value == "") {
            approachY_form.setCustomValidity(`Обязательно укажите приближение по оси Y`);
            approachY_form.reportValidity()
            return false
        } else{
            approachY_form.setCustomValidity("");
            return true
        }
    }

    const check_radius_low = () => {
        radius.value[0] = low_radius_form.value.replace(',', '.')
        if (!check_format(low_radius_form)) {}
        else if (Number(radius.value[0]) < 0){
            radius.value[0] = 0
        } else if (Number(radius.value[0]) > 100){
            radius.value[0] = 100
        } else if (low_radius_form.value == "") {
            low_radius_form.setCustomValidity(`Обязательно укажите радиус по оси x`);
            low_radius_form.reportValidity();
        } else{
            low_radius_form.setCustomValidity("");
        }
    }

    const check_radius_high = () => {
        radius.value[1] = high_radius_form.value.replace(',', '.')
        if (!check_format(high_radius_form)) {}
        else if (Number(radius.value[1]) > 100){
            radius.value[1] = 100
        } else if (Number(radius.value[1]) < 0){
            radius.value[1] = 0
        } else if (high_radius_form.value == "") {
            high_radius_form.setCustomValidity(`Обязательно укажите радиус по оси y`);
            high_radius_form.reportValidity();
        } else{
            high_radius_form.setCustomValidity("");
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

    const check_solve = () => {     
        if(
        check_method() &&
        check_equalization() &&
        check_approachX() &&
        check_approachY() &&
        check_accuracy()
        ){
        sent_data()
        } else {
            response.value = null
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

</script>

<style scoped>

    #approach {
        justify-content: space-between;
    }

    #approach input {
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

    #radius-adjustment {
        padding: 2vh;
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-wrap: wrap;
        width: 40%;
        height: 20vh;
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

    #result {
        display: flex;
        justify-content: space-around;
        align-items: flex-start;
        flex-wrap: wrap;
        padding: 2vh;
        width: 40%;
        min-height: 20vh;
    }
    
    #result p{
        font-size: 2vh;
        text-align: center;
    }

    #result div{
        font-size: 3vh;
        text-align: center;
        width: 70%;
    }

    #download {
        font-size: 1vw;
        padding: 0.5vh 1vw;
    }


</style>
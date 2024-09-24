<template>
    <div class="main-wrapper">
        <h1>Lab3</h1>
        <Task value="Пользователю предлагается выбрать уравнение, выбрать метод решения, ввести исходные данные и посмотреть на результат - найденный на заданном промежутке интеграл от уравнения"/>
        <div id="adjustment" class="rounded">
                <form>
                Выберите метод:
                <div id="methods-file" class="custom-select">
                    <select id="method" v-model="method" style="margin-top: 10px;">
                        <option value="-1">---</option>
                        <option value="1">Метод прямоугольников (левый)</option>
                        <option value="2">Метод прямоугольников (средний)</option>
                        <option value="3">Метод прямоугольников (правый)</option>
                        <option value="4">Метод трапеций</option>
                        <option value="5">Метод Симпсона</option>
                    </select>
                </div>
                Выберите уравнение:
                <div class="custom-select">
                    <select id="equalization" v-model="equation">
                        <option value="-1">---</option>
                        <option value="1"> y = x</option>
                        <option value="2">y = x&sup2</option>
                        <option value="3">y = 1 / x</option>
                    </select>
                </div>
                Укажите промежуток:
                <div id="interval" class="custom-input">
                    <input id="left" placeholder="Начало" type="text" maxlength="7" @input="check_left"/>
                    <input id="right" placeholder="Конец" type="text" maxlength="7" @input="check_right"/>
                </div>
                Укажите точность:
                <div class="custom-input">
                    <input id="accuracy" placeholder="Укажите число от 0 до 0.1" type="text" maxlength="15" @input="check_accuracy" />
                </div>
                <button type="button" id="solve" @click="check_solve">Решить!</button>
            </form>
        </div>
        <Result :value="response" />
    </div>
</template>

<script setup>
    import Task from '../components/Task.vue';
    import Result from '../components/Result.vue';
    import {ref, onMounted} from 'vue';

    const method = ref(-1)
    const equation = ref(-1)
    const left = ref(0)
    const right = ref(10)
    const accuracy = ref()

    const response = ref()

    const send_data = () => {
        $.get('/solve/Lab3', {
            method: method.value,
            equation: equation.value,
            left: left.value,
            right: right.value,
            accuracy: accuracy.value
        }, function(resp){
            if (resp.status == 1){
                response.value = {'Значение интеграла:' : resp.value, 'Число разбиения интервала:' : resp.number}
            } else if (resp.status == 2){
                response.value = {'Значение интеграла:' : resp.value, 'Число разбиения интервала:' : resp.number, 'Warning!' : resp.warning}
            } 
            else {
                response.value = {'Ошибка' : resp.error}
            }
        }); 
    }

    onMounted(() => {
        $('#download').on('click', function(){
            if (response.value != null){
                let link = document.createElement('a');
                link.setAttribute('href', '/download/Lab3');
                link.setAttribute('download', 'temp_integral.txt');
                link.click();
            }
            return true;
        });
    })

    /* Валидаця */

    let left_form = null
    let right_form = null
    let accuracy_form = null
    let equalization_form = null
    let method_form = null

    onMounted(() => {
        left_form = document.getElementById('left')
        right_form = document.getElementById('right')
        accuracy_form = document.getElementById('accuracy')
        equalization_form = document.getElementById('equalization')
        method_form = document.getElementById('method')
    })

    const check_equalization = () => {
        if (equation.value == -1){
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

    const check_left = () => {
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

    const check_right = () => {
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
        check_left() &&
        check_right() &&
        check_accuracy()      
        ){
        send_data()
        } else {
            response.value = null
        }
    }   
    
</script>

<style scoped>
    #adjustment {
        margin: 0 20vw;
        padding: 2vh 5vh;
        display: flex;
        justify-content: space-around;
        align-items: center;
        flex-wrap: wrap;
        text-align: center;
    }
    #solve {
        font-size: 3vw;
        padding: 1vh 2vw;
    }
    form {
        width: 100%
    }
    #interval {
        justify-content: space-between;
    }
    #interval input {
        width: 40%;
        padding: 0.675em 1em 0.675em 1em;
    }
</style>

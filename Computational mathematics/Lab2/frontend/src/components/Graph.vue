<template>
    <Scatter
    id="my-chart-id"
    :options="chartOptions"
    :data="chartData"
  />
</template>

<script setup>
    import { Scatter } from 'vue-chartjs'
    import { Chart as ChartJS, CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler} from 'chart.js'
    import { computed, onBeforeMount } from 'vue';



    onBeforeMount(() => {
        ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement, Title, Tooltip, Legend, Filler)
    })

    const props = defineProps({
        bottom: String,
        top: String,
        equation: {
            type: Number,
            default: ""
        },
        equation_system: {
            type: Number,
            default: ""
        },
        left: {
            type: String,
            default: '0'
        },
        right: {
            type: Number,
            default: '10'
        } 
    })
    
    const chartData = computed(() => {
        return {
            datasets: [{
                borderColor: "#6e5b7b",
                borderWidth: 2,
                data: generate_equation(),
                tension: 0.4
            },
            {
                borderColor: "#6e5b7b",
                borderWidth: 2,
                data: generate_equation_system().first,
                tension: 0.4
            },
            {
                borderColor: "#6e5b7b",
                borderWidth: 2,
                data: generate_equation_system().second,
                tension: 0.4
            }]
        }
    })
    const chartOptions = computed(() => {
        return {
            aspectRatio: 1,
            showLine: true,
            elements: {
                point:{
                    radius: 0
                },
            },
            plugins: {
                    legend: {
                        display: false
                    },
            },
            scales: {
                    y : {
                        max : set_range_y().max,
                        min : set_range_y().min,
                    },
                    x : {
                        max : set_range_x().max,
                        min : set_range_x().min,
                    }
            },
        }
    })

    function set_range_x(){
        console.log(props.left, props.right)
        let ret_arr = {min: 0, max: 10}
        if (isNaN(props.left) || isNaN(props.right) || Number(props.right) <= Number(props.left)){
            console.log("gpu")
            return ret_arr;
        } else {
            console.log("fd")
            return {min: Number(props.left), max: Number(props.right)}
        }
    }

    function set_range_y(){
        console.log('height', props.bottom, props.top)
        let ret_arr = {min: -30, max: 30}
        if (isNaN(props.bottom) || isNaN(props.top) || Number(props.top) <= Number(props.bottom)){
            return ret_arr;
        } else {
            return {min: Number(props.bottom), max: Number(props.top)}
        }
    }


    function generate_equation(){
        const left = Number(String(props.left).replace(',', '.'))
        const right = Number(String(props.right).replace(',', '.'))
        const equation = Number(props.equation) 
        const step = (right - left) / 1000
        const result_arr = []
        if (isNaN(left) || isNaN(right)){
            return result_arr
        }
        switch(equation){
            case 1:
                for(let i = left; i < right; i += step){
                    result_arr.push({
                        x: i,
                        y: 2.74*i**3 - 1.93*i**2 - 15.28*i - 3.72
                    })
                }
                break
            case 2:
                for(let i = left; i < right; i += step){
                    result_arr.push({
                        x: i,
                        y: -1.38*i**3 - 5.42*i**2 + 2.57*i + 10.95
                    })
                }
                break
            case 3:
                for(let i = left; i < right; i += step){
                    result_arr.push({
                        x: i,
                        y: i**3 + 2.84*i**2 - 5.606*i - 14.766
                    })
                }
                break  
            case 4:
                for(let i = left; i < right; i += step){
                    result_arr.push({
                        x: i,
                        y: Math.sin((i + 1)) - 0.2
                    })
                }
                break     
        }
        return result_arr
    }

    function generate_equation_system(){
        const left = Number(String(props.left).replace(',', '.'))
        const right = Number(String(props.right).replace(',', '.'))
        const equation_system = Number(props.equation_system) 
        const step = (right - left) / 1000
        const result_arr = {first: [], second: []}
        if (isNaN(left) || isNaN(right)){
            return result_arr
        }
        switch(equation_system){
            case 1:
                for(let i = left; i < right; i += step){
                    result_arr.first.push({
                        x: i,
                        y: Math.sin((i + 1)) - 1.2
                    })
                }
                for(let i = left; i < right; i += step){
                    result_arr.second.push({
                        x: i,
                        y: (Math.cos(i) - 2) / 2
                    })
                }
                break
            case 2:
                for(let i = left; i < right; i += step){
                    result_arr.first.push({
                        x: i,
                        y: 0.5 - Math.cos(i - 1)
                    })
                }
                result_arr.second.push({
                        x: -(Math.sqrt(2)),
                        y: 0
                })
                for(let i = -(Math.sqrt(2)) + 0.0001; i < (Math.sqrt(2)); i += 0.01){
                    result_arr.second.push({
                        x: i,
                        y: Math.sqrt(2 - i**2)
                    })
                }
                result_arr.second.push({
                        x: (Math.sqrt(2)),
                        y: 0
                })
                for(let i = (Math.sqrt(2)) - 0.0001; i > -(Math.sqrt(2)); i -= 0.01){
                    result_arr.second.push({
                        x: i,
                        y: -Math.sqrt(2 - i**2)
                    })
                }
                result_arr.second.push({
                        x: -(Math.sqrt(2)),
                        y: 0
                })
                break
        }
        return result_arr
    }
    
</script>

<style scoped>

</style>
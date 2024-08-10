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
        x_values : [Number],
        y_values : [Number],
        current_list : Number,
        linear : Object,
        general : Object,
        quadratic : Object,
        cubic : Object,
        degree : Object,
        exp : Object,
        log : Object
    })
    
    const chartData = computed(() => {
        return {
            datasets: [
            {
                pointBackgroundColor: "#ff0000",
                borderWidth: 0,
                data: validate_points(),
                tension: 0.4,
                elements: {
                    point:{
                        radius: 2
                    },
                },
            },
            {
                borderColor: "#6e5b7b",
                borderWidth: 2,
                data:build_graph(),
                tension: 0.4
            }
        ]
        }
    })
    const chartOptions = computed(() => {
        return {
            aspectRatio: 1,
            showLine: true,
            plugins: {
                    legend: {
                        display: false
                    },
            },
            elements: {
                point:{
                    radius: 0
                },
            },
            animation: {
                duration: 0
            },
            scales: {
                    y : {
                        max : get_graph_border().top,
                        min : get_graph_border().bottom,
                    },
                    x : {
                        max : get_graph_border().right,
                        min : get_graph_border().left,
                    }
            },
        }
    })

    const validate_points = () => {
        let ret_arr = []
        for (let i = 0; i < props.x_values.length; i++){
            if (props.x_values[i] !== "" && props.y_values[i] !== ""){
                ret_arr.push({x: String(props.x_values[i]).replace(',', '.'), y: String(props.y_values[i]).replace(',', '.')})
            }
        }
        return ret_arr
    }

    const get_graph_border = () => {
        let flag = 0
        for (let x of props.x_values){
            if (x != undefined){
                flag = 1
                break;
            }
        }
        if (flag == 0){
            return {left : 0, right : 1, top : 1, bottom : 0 } 
        }
        let borders = define_border()
        let x_ratio = (borders.right - borders.left) * 0.15
        let y_ratio = (borders.top - borders.bottom) * 0.15
        if (borders.right == borders.left){
            x_ratio = 1
        }
        if (borders.top == borders.bottom){
            y_ratio = 1
        }
        return { left : borders.left - x_ratio, right : borders.right + x_ratio,
         bottom : borders.bottom - y_ratio, top : borders.top + y_ratio }
    }

    const define_border = () => {
        let right = -10000000
        let left = 100000
        for (let x of props.x_values){
            x = Number(x)
            if (x < left){
                left = x
            }
            if (x > right){
                right = x
            }
        }
        let top = -100000000
        let bottom = 100000000
        for (let y of props.y_values){
            y = Number(y)
            if (y < bottom){
                bottom = y
            }
            if (y > top){
                top = y
            }
        }
        return {left : left, right : right, top : top, bottom : bottom }
    }

    const build_graph = () => {
        if (props.general != undefined){
            let border = get_graph_border()
            switch (props.current_list){
            case 1:
                return generate_linear_points(props.linear.a, props.linear.b, border)
            case 2:
                return generate_quadratic_points(props.quadratic.a, props.quadratic.b, props.quadratic.c, border)
            case 3:
                return generate_cubic_points(props.cubic.a, props.cubic.b, props.cubic.c, props.cubic.d, border)
            case 4:
                return generate_exp_points(props.exp.a, props.exp.b, border)
            case 5:
                return generate_log_points(props.log.a, props.log.b, border)
            case 6:
                return generate_degree_points(props.degree.a, props.degree.b, border)
            }
        }
        return []
    }

    const generate_linear_points = (a, b, border) => {
        let result_arr = []
        const step = (border.right - border.left) / 1000
        for (let x = border.left; x <= border.right; x += step){
            result_arr.push({x : x, y: a * x + b})
        }
        return result_arr
    }

    const generate_quadratic_points = (a, b, c, border) => {
        let result_arr = []
        const step = (border.right - border.left) / 1000
        for (let x = border.left; x <= border.right; x += step){
            result_arr.push({x : x, y: a * x**2 + b * x + c})
        }
        return result_arr
    }

    const generate_cubic_points = (a, b, c, d, border) => {
        let result_arr = []
        const step = (border.right - border.left) / 1000
        for (let x = border.left; x <= border.right; x += step){
            result_arr.push({x : x, y: a * x**3 + b * x**2 + c * x + d})
        }
        return result_arr
    }

    const generate_exp_points = (a, b, border) => {
        let result_arr = []
        const step = (border.right - border.left) / 1000
        for (let x = border.left; x <= border.right; x += step){
            result_arr.push({x : x, y: a * Math.exp(b * x)})
        }
        return result_arr
    }

    const generate_log_points = (a, b, border) => {
        let result_arr = []
        const step = (border.right - border.left) / 1000
        for (let x = border.left; x <= border.right; x += step){
            result_arr.push({x : x, y: a * Math.log(x) + b})
        }
        return result_arr
    }

    const generate_degree_points = (a, b, border) => {
        let result_arr = []
        const step = (border.right - border.left) / 1000
        for (let x = border.left; x <= border.right; x += step){
            result_arr.push({x : x, y: a * x**b})
        }
        return result_arr
    }


    
</script>

<style scoped>

</style>
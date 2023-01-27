Vue.component('centro', {
    props: ['title'],
    template: '<article><h1>{{ title }}</h1><p>Testo.</p></article>'
})

const app = Vue.createApp({
    //data, function ,template
    data(){
        return {
            show_book:true,
            act_book_title:'Game of thrones',
            flag: true,
            x:0,
            y:0
        }
    },

    methods:{
        togle(){
            this.show_book=!this.show_book
        },
        handleevent(click_type,numeber){
            console.log(click_type.type,numeber)
        },
        handleMouseMovement(e){
            this.x=e.offsetX
            this.y=e.offsetY
        }


    }



})

app.mount('#app')
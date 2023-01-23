app = Vue.createApp({

    data(){
        return {
            firstName : 'Olly',
            surname : 'GG',
            email : 'olly@gmail.com',
            pf : 'https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885__480.jpg',
            primaPagina: true,
            secondaPagina: false
        }
    },
    methods:{
        getUser :  function (){
            alert(this.firstName)
        },
        transit : function() {
            this.secondaPagina = true;
            this.primaPagina = false;
        }
    }
}).mount('#app')
<template>
    <v-footer class="footer">
        <v-col class="text-center foot-con" cols="2">
            <span>&copy; A802</span>
            <v-icon class="pb-1">mdi-all-inclusive</v-icon>
            <span>SPOPARTY</span>
        </v-col>
    </v-footer>
</template>

<script setup>

</script>

<style lang="scss" scoped>
.test {
    color:#0d0742;
    color: #08042B;
    color: #333D51;
}

.footer {
    background-color:#223355;
    color: #D3AC2B;
    // background: linear-gradient(135deg, #223355, #334466);
    // color: #D3AC2B;
    height: 10vh;
    max-height: 80px;
    margin-top: 9vh;
}

span {
    margin : 0 10px;
}
.foot-con {
    margin: auto;
    // transform: rotate(360deg);
    transition: transform 0.5s ease;

    &:hover {
        animation: shake 0.5s ease-in-out infinite;
        // transform: scale(1.15);
        // transform: rotate(360deg);
    }
}

@keyframes shake {
  0% {
    transform: rotate(-1deg) scale(1.1);
  }
  20% {
    transform: rotate(2deg) scale(1.1);
  }
  40% {
    transform: rotate(-4deg) scale(1.1);
  }
  60% {
    transform: rotate(6deg) scale(1.1);
  }
  80% {
    transform: rotate(-4deg) scale(1.1);
  }
  100% {
    transform: rotate(1deg) scale(1.1);
  }
}
</style>
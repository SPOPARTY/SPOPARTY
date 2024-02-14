<template>
    <v-footer class="footer">
        <v-col class="text-center foot-con" cols="12">
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
    max-height: 9vh;
    margin-top: 9vh;
}

span {
    margin : 0 10px;
}
.foot-con {
    // transform: rotate(360deg);
    transition: transform 0.5s ease;

    &:hover {
        transform: scale(1.15);
        // transform: rotate(360deg);
    }
}

</style>
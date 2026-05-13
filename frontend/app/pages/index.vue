<script lang="ts" setup>
const {data: status, error} = useLazyFetch('/api/health')
</script>

<template>
  <div class="flex flex-col justify-center items-center w-full h-screen">
    <p v-if="!error">status: {{ status ? status.status : error }}</p>
    <p v-else>error: {{ error }}</p>
  </div>
</template>

<style scoped>

</style>
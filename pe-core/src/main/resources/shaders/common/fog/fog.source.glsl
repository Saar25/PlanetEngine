/**
*
* Fog source file
*
**/

float calcFogAmount(float distance, Fog fog) {
    float amount = (fog.end - distance) / (fog.end - fog.start);
    return smoothstep(0, 1, clamp(amount, 0, 1));
}

vec3 calcFogColor(float distance, Fog fog) {
    float amount = calcFogAmount(distance, fog);
    return amount * fog.color;
}

vec3 applyFogColor(float distance, Fog fog, vec3 color) {
    float amount = calcFogAmount(distance, fog);
    return mix(fog.color, color, amount);
}
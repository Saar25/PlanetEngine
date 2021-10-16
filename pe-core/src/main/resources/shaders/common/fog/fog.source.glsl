/**
*
* Fog source file
*
**/

float calcFogAmount(float distance, Fog fog) {
    float amount = (fog.end - distance) / (fog.end - fog.start);
    return smoothstep(0, 1, clamp(amount, 0, 1));
}

vec3 calcFogColour(float distance, Fog fog) {
    float amount = calcFogAmount(distance, fog);
    return amount * fog.colour;
}

vec3 applyFogColour(float distance, Fog fog, vec3 colour) {
    float amount = calcFogAmount(distance, fog);
    return mix(fog.colour, colour, amount);
}
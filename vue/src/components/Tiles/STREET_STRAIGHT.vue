<!-- eslint-disable vue/multi-word-component-names -->
<script setup lang="ts">
//@ts-ignore
import * as THREE from "three";
import { ObjectEnum } from "@/services/ObjectEnum";
import { GltfModel } from "troisjs";
import TRAFFIC_LIGHT from "@/components/objects/TRAFFIC_LIGHT.vue";
import GAS_STATION from "@/components/objects/GAS_STATION.vue";
import STREET_STRAIGHT_URL from "@/assets/models/STREET_STRAIGHT.glb?url";
import TRAFFIC_LIGHT_LIGHT from "@/components/objects/TRAFFIC_LIGHT_LIGHT.vue";
import { computed } from "vue";

const props = withDefaults(
  defineProps<{
    width: number;
    height: number;
    position: THREE.Vector3;
    rotation: THREE.Vector3;
    type: string;
    placedObject: any;
    orientation: string;
    trafficLightState: any;
  }>(),
  { width: 10, height: 10 }
);

const trafficLight = computed(() => {
  if (props.trafficLightState == "NORTHSOUTH") {
    if (props.orientation == "WEST" || props.orientation == "EAST") {
      return {
        redOn: true,
        greenOn: false,
        yellowOn: false,
      };
    } else if (props.orientation == "NORTH" || props.orientation == "SOUTH") {
      return {
        redOn: false,
        greenOn: true,
        yellowOn: false,
      };
    }
  } else if (props.trafficLightState == "EASTWEST") {
    if (props.orientation == "WEST" || props.orientation == "EAST") {
      return {
        redOn: false,
        greenOn: true,
        yellowOn: false,
      };
    } else if (props.orientation == "NORTH" || props.orientation == "SOUTH") {
      return {
        redOn: true,
        greenOn: false,
        yellowOn: false,
      };
    }
  }
  return {
    redOn: false,
    greenOn: false,
    yellowOn: true,
  };
});

const trafficLightLeft = "trafficLightLeft";
const trafficLightRight = "trafficLightRight";
const gasStation = "gasStation";

function setPosition(orientation: string, name: string): THREE.Vector3 {
  if (orientation == "WEST" || orientation == "EAST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(3, 0, -6);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(-3, 0, 6);
    } else if (name == "gasStation") {
      return new THREE.Vector3(1, -0.3, 6.5);
    }
  } else if (orientation == "NORTH" || orientation == "SOUTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(6, 0, 3);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(-6, 0, -3);
    } else if (name == "gasStation") {
      return new THREE.Vector3(-6.5, -0.3, 0);
    }
  }
  return new THREE.Vector3(0, 0, 0);
}

function setRotation(orientation: string, name: string): THREE.Vector3 {
  if (orientation == "WEST" || orientation == "EAST") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 1.57, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 4.71, 0);
    } else if (name == "gasStation") {
      return new THREE.Vector3(0, 3.14, 0);
    }
  } else if (orientation == "NORTH" || orientation == "SOUTH") {
    if (name == "trafficLightLeft") {
      return new THREE.Vector3(0, 0, 0);
    } else if (name == "trafficLightRight") {
      return new THREE.Vector3(0, 3.14, 0);
    } else if (name == "gasStation") {
      return new THREE.Vector3(0, 4.71, 0);
    }
  }
  return new THREE.Vector3(0, 0, 0);
}
</script>
<template>
  <GltfModel
    ref="model"
    :src="STREET_STRAIGHT_URL"
    :position="props.position"
    :rotation="props.rotation"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightLeft))
    "
    :rotation="setRotation(props.orientation, trafficLightLeft)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightLeft))
    "
    :angle="3"
    :red="trafficLight.redOn"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOn"
  />
  <TRAFFIC_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightRight))
    "
    :rotation="setRotation(props.orientation, trafficLightRight)"
    :type="ObjectEnum.TRAFFIC_LIGHT"
  />
  <TRAFFIC_LIGHT_LIGHT
    v-if:="props.placedObject === ObjectEnum.TRAFFIC_LIGHT"
    :position="
      props.position
        .clone()
        .add(setPosition(props.orientation, trafficLightRight))
    "
    :angle="3"
    :red="trafficLight.redOn"
    :yellow="trafficLight.yellowOn"
    :green="trafficLight.greenOn"
  />
  <GAS_STATION
    v-if:="props.placedObject === ObjectEnum.GAS_STATION"
    :position="
      props.position.clone().add(setPosition(props.orientation, gasStation))
    "
    :rotation="setRotation(props.orientation, gasStation)"
    :type="ObjectEnum.GAS_STATION"
  ></GAS_STATION>
</template>

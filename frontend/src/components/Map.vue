<script setup>
    import { ref, onMounted } from 'vue';
    import axios from 'axios';
    import { useRouter } from 'vue-router';
    import { useKakao } from 'vue3-kakao-maps/@utils';
    
    useKakao('eb23c25e315167fe3636e6b85fdd2b87');
    
    const baseUrl = 'http://localhost:8080/v1/api/event';
    const list = ref([]);
    const router = useRouter();
    
    const requestAPI = async () => {
        try {
            const result = await axios.get(`${baseUrl}/list`);
            list.value = result.data;
            console.log(list.value);
        } catch (error) {
            console.error('Failed to fetch data:', error);
        }
    };
    
    const createInfoWindowContent = (title, description, imageUrl) => {
        const content = `
            <div style="display: flex; flex-direction: column; align-items: center; padding: 5px; width: 300px; height: auto; margin-bottom: 5px; ">
                ${imageUrl ? `<img src="${imageUrl}" style="width: 100%; height: auto; max-height: 150px; object-fit: cover; margin-bottom: 5px;" />` : ''}
                <strong>${title}</strong>
                
            </div>`;
        return content;
    };
    
    const initializeMap = () => {
        const mapContainer = document.getElementById('map');
        const mapOption = {
            center: new kakao.maps.LatLng(36.250705, 127.370667),
            level: 13
        };
    
        const map = new kakao.maps.Map(mapContainer, mapOption);
    
        const markers = list.value.map(event => {
            const lat = parseFloat(event.x);
            const lng = parseFloat(event.y);
    
            if (isNaN(lat) || isNaN(lng)) {
                console.error(`Invalid coordinates for event ${event.no}: ${lat}, ${lng}`);
                return null;
            }
    
            const content = createInfoWindowContent(event.title, event.description, event.imageUrl);
    
            const marker = new kakao.maps.Marker({
                position: new kakao.maps.LatLng(lat, lng),
                title: event.title
            });
    
            const infoWindow = new kakao.maps.InfoWindow({
                content
            });
    
            kakao.maps.event.addListener(marker, 'mouseover', () => {
                infoWindow.open(map, marker);
            });
    
            kakao.maps.event.addListener(marker, 'mouseout', () => {
                infoWindow.close();
            });
    
            return marker;
        }).filter(marker => marker !== null);
    
        markers.forEach((marker, index) => {
            kakao.maps.event.addListener(marker, 'click', () => {
                const id = list.value[index]?.no;
                if (id) {
                    router.push({ path: '/event', query: { id } });
                }
            });
        });

        const markerClusterer = new kakao.maps.MarkerClusterer({
            map: map,
            averageCenter: true,
            minLevel: 10,
            disableClickZoom: true
        });
    
        markerClusterer.addMarkers(markers);
    
        kakao.maps.event.addListener(markerClusterer, 'clusterclick', function(cluster) {
            let level = map.getLevel() - 1;
            map.setLevel(level, {anchor: cluster.getCenter()});
        });
        
    };
    
    onMounted(() => {
        requestAPI().then(() => {
            initializeMap();
        });
    });
    </script>
    
    <template>
        <div id="map" style="width: 100%; height: 800px;"></div>
    </template>
    
    <style>
    /* 스타일을 추가하세요 */
    </style>
    
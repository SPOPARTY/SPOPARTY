  <template>
      <v-dialog 
        v-model="isModalVisible" 
        max-width="800px"
        @click:outside="closeModal"
        persistent      >
        <v-card>
          <v-row>
            <v-col>
              <v-card-title style="margin-left:330px;">팔로우 리스트</v-card-title>
            </v-col>
            <v-col class="text-right">
              <v-btn :ripple="false" @click="closeModal" class="no-background-hover">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-col>
          </v-row>

          <v-row justify="center">
              <v-col cols="5">
                  <v-btn 
                      color="#333D51" 
                      style="width:100%"
                      @click="showAllClubs">
                      전체
                  </v-btn>
              </v-col>   
              <v-col cols="5">
                  <v-btn 
                      color="#333D51" 
                      style="width:100%"
                      @click="showFollwingClubs">
                      내가 팔로우 한 구단
                  </v-btn>
              </v-col>
          </v-row>
    
          <v-card-text>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="검색"
              single-line
              hide-details
            ></v-text-field>
    
            <v-divider></v-divider>
    
            <v-list three-line>
              <v-virtual-scroll
                :items="filterFollowClub"
                height="300"
                item-height="68"
              >
                <template v-slot="{ item }">
                  <v-list-item>
                    <v-row>
                      <v-col cols="3">
                        <v-list-item-avatar>
                          <img :src="item.logo" :alt="item.nameKr" style="width:64px; height:64px;">
                        </v-list-item-avatar>
                      </v-col>
                      <v-col cols="6">
                        <v-list-item-content class="grow">
                          <v-list-item-title>{{ item.nameKr }}</v-list-item-title>
                          <v-list-item-subtitle>{{ item.nameEng }}</v-list-item-subtitle>
                        </v-list-item-content>
                      </v-col>
                      <v-col cols="3">
                        <v-list-item-action>
                          <v-btn class="button" icon v-if="!clubData.following.some(followedClub => followedClub.id === item.id)" @click="followClub(item)">
                            <v-icon>mdi-plus</v-icon>
                          </v-btn>

                          <v-btn class="button" icon v-if="clubData.following.some(followedClub => followedClub.id === item.id) && clubData.current == clubData.following" @click="unfollowClub(item.id)">
                            <v-icon>mdi-delete</v-icon>
                          </v-btn>
        

                        </v-list-item-action>
                      </v-col>
                    </v-row>
                  </v-list-item>
                  <v-divider inset></v-divider>
                </template>
              </v-virtual-scroll>
            </v-list>
          </v-card-text>
        </v-card>
      </v-dialog>
  </template>

  <script setup>
  import { ref, reactive, computed } from 'vue';
  import {useFollowStore} from "@/stores/member/follows"

  const isModalVisible = ref(true);

  const followStore = useFollowStore();

  const props = defineProps({
      teamList : Object,
      followList : Object,
      memberId : Number,

  })

  const emit = defineEmits([
      'FollowListClose',
      'FollowClub',
      'UnfollowClub'
  ])

  const memberId = props.memberId;


  // 검색어 찾기
  const search = ref('');

  // 전체 구단 목록 더미 데이터
  const allClubs = props.teamList;

  // 팔로우 중인 구단 목록 더미 데이터
  const followingClubs = computed(() => {
      if (!props.followList || props.followList.length === 0){
          return [];
      }
      // console.log("실행실행")
      const followedTeamIds = props.followList.map(follow => follow.teamId); // map을 통해 객체 배열을 teamId 배열로 변환
      return allClubs.filter(team => followedTeamIds.includes(team.id));
  })

  const clubData = reactive({
      current : allClubs, // 모달창이 띄워지면 보여지는 값
      all: allClubs, // 전체 구단 목록
      following: followingClubs.value // 팔로우하고 있는 구단목록
  })

  const showAllClubs = () => {
      clubData.current = clubData.all;
      // console.log("clubs ->" , clubData.current)
  }

  const showFollwingClubs = () => {
      clubData.current = clubData.following;
      // console.log("clubs ->" , clubData.current)
  }

  // 구단 검색
  const filterFollowClub = computed(() => {
      if (!search.value) {
          return clubData.current
      }
      return clubData.current.filter((club) => club.nameKr.includes(search.value))
  })

  // 구단을 팔로우
  const followClub = (club) => {
      // 이미 팔로우 중인지 확인
      if(!clubData.following.includes(club)) {
        if(confirm("해당 구단을 팔로우 하시겠습니까?") === true) {
          followStore.doFollow(club.id); // club.id가 곧 teamId
          clubData.following.push(club);
          if (clubData.current === clubData.following){
            clubData.current = [...clubData.following];
          }
        }
      }
      // 현재 보여지고 있는 목록이 팔로잉 목록일 경우 업데이트
      emit('FollowClub',followingCount)
  }

    // 구단을 언팔로우
  const unfollowClub = (id) => {
    // console.log("teamid ", id)
    if (confirm("해당 구단을 팔로우 취소하시겠습니까?") === true) {
        followStore.doUnFollow(id);

        const indexInFollowing = clubData.following.findIndex(club => club.id === id);
        if (indexInFollowing !== -1) {
            clubData.following.splice(indexInFollowing, 1);
        }
        const indexInCurrent = clubData.current.findIndex(club => club.id === id);
        if (indexInCurrent !== -1) {
            clubData.current.splice(indexInCurrent, 1);
        }
    }

      emit('UnfollowClub', followingCount)
  };  


  // 팔로우 중인 구단 수 
  const followingCount = computed(() => {
      return clubData.following.length;
  })


  // 모달창 닫기
  function closeModal() {
      isModalVisible.value = false;
      emit('FollowListClose')
  }
  </script>

  <style scoped lang="scss">
  .no-background-hover {
    box-shadow: none !important;

    &:hover {
      background-color: transparent !important;
    }
  }

  .button {
      color : #292646;
      box-shadow: none !important;
      background : transparent !important;
  }

  </style>
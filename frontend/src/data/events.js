const eventArr = [
    {
        id:0,
        title:'서울대X관악구 캠퍼스타운 2024년 스타트업 아카데미',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/snucampustown/79982/ProjectInfo/Cover/9ae3894f551d41e3b165ddbc65dfad27.jpg',
        eventDate: '08월26일(월)',
        price: '무료',
        region: '서울/경기/인천',
        online: true,
        offline: false,
        views: 5975,
        scrap:false
    },
    {
        id:1,
        title:'Beyond Borders: 한국 B2B SaaS 스타트업을 위한 글로벌 진출 전략 세미나',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/sparklabs-demoday12/88048/ProjectInfo/Cover/29a292e4e86e44cebe25ad72929b975e.jpg',
        eventDate: '2024-08-29',
        price: '무료',
        region: '충청/대전/세종',
        online: true,
        offline: true,
        views: 851,
        scrap:false
    },
    {
        id:2,
        title:'8월 WIST 오픈 예배',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/wlworship/88291/ProjectInfo/Cover/432563be9e2b43f18667185d5c46e684.jpg',
        eventDate: '2024-08-29',
        price: '무료',
        region: '서울/경기/인천',
        online: true,
        offline: false,
        views: 563,
        scrap:false
    }
    ,
    {
        id:3,
        title:'제2회 About Empathy 공감 콘서트',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/proejctplanet/87806/ProjectInfo/Cover/7d4ec43725d64d37b948d3618b8785c8.jpg',
        eventDate: '08월17일(토) 마포구',
        price: '13,000',
        region: '부산/울산/경남',
        online: true,
        offline: false,
        views: 892,
        scrap:false
    },
    {
        id:4,
        title:'빅데이터 자바개발자-하이미디어아카데미 강릉점',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/ithimedia/87774/ProjectInfo/Cover/6cb053761e78482899446181fca78c69.jpg',
        eventDate: '2024-08-26',
        price: '무료',
        region: '강원',
        online: false,
        offline: true,
        views: 5,
        scrap:false
    },

    {
        id:5,
        title:'토지투자 A to Z',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/buja/80806/ProjectInfo/Cover/952bc3dfdac049b3bdf04a058892f8e6.png',
        eventDate: '08년17일(토)',
        price: '무료',
        region: '대구/경북',
        online: true,
        offline: false,
        views: 577,
        scrap:false
    }
,
    {
        id:6,
        title:'서울대X관악구 캠퍼스타운 2024년 스타트업 아카데미',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/snucampustown/79982/ProjectInfo/Cover/9ae3894f551d41e3b165ddbc65dfad27.jpg',
        eventDate: '08년26일(월)',
        price: '무료',
        region: '제주',
        online: true,
        offline: false,
        views: 20,
        scrap:false
    }
,
    {
        id:7,
        title:'(6회차) 제주 창업생태계 포럼 X 해양수산 인베스터데이',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/jcceiforever/89596/ProjectInfo/Cover/4b03674cb34048efa8e88797f33bee73.jpg',
        eventDate: '2024-08-29',
        price: '무료',
        region: '제주',
        online: true,
        offline: false,
        views: 100,
        scrap:false
    }
,
    {
        id:8,
        title:'2024 강남 취•창업허브센터 포럼 : 3회. 푸드테크의 혁신',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/gangnamopk12/88584/ProjectInfo/Cover/90b70b4958f740068a856ebd18f49873.jpg',
        eventDate: '2024-08-27',
        price: '무료',
        region: '강원',
        online: true,
        offline: false,
        views: 11,
        scrap:false
    }

    ,


    {
        id:9,
        title:'[Udemy 웨비나] 격동의 AI시대, 살아남기 위해 우리 조직은 어떻게 해야할까?',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/udemywjtb/87891/ProjectInfo/Cover/e6c4b08fa7c640a69ddc15409d94a9ba.jpg',
        eventDate: '2024-08-27',
        price: '무료',
        region: '서울/경기/인천',
        online: true,
        offline: false,
        views: 7,
        scrap:false
    }
,
    {
        id:10,
        title:'자산관리 컨설팅 세미나',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/buja/86888/ProjectInfo/Cover/a1750803a0e4446b9d89e4434ca23715.png',
        eventDate: '2024-08-17',
        price: '무료',
        region: '서울/경기/인천',
        online: true,
        offline: false,
        views: 182,
        scrap:false
    }
,
    {
        id:11,
        title:'[여름 무료 한정] 내 인생 첫 재테크',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/buildupestate/85219/ProjectInfo/Cover/baef89ca582849f795b374f8ca36f54c.jpg',
        eventDate: '2024-08-31',
        price: '무료',
        region: '서울/경기/인천',
        online: true,
        offline: true,
        views: 0,
        scrap:false
    },
    {
        id:12,
        title:'Busan NE(O)RDINARY DemoDay : FLASH',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/makeuschallenge/88917/ProjectInfo/Cover/d9239e9708a04571b6799c65bed7f2f4.png',
        eventDate: '2024-08-31',
        price: '무료',
        region: '부산/울산/경남',
        online: false,
        offline: true,
        views: 195,
        scrap:false
    }
    ,
    {
        id:13,
        title:'[무료 세미나] 소프트웨어 해외 외주개발 사례 공유 - 비용은 절감하고 생산성은 높여보자!',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/techready/87892/ProjectInfo/Cover/9b6ec509665949449f633253bf154869.png',
        eventDate: '2024-08-28',
        price: '무료',
        region: '서울/경기/인천',
        online: false,
        offline: true,
        views: 105,
        scrap:false
    },
    {
        id:14,
        title:'재테크 함께해요! :1대1클래스로 심도있게!',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/pension/79759/ProjectInfo/Cover/999ffeca75e3417e862475a3a4c983cc.png',
        eventDate: '2024-08-15',
        price: '2,000',
        region: '전라/광주',
        online: false,
        offline: true,
        views: 320,
        scrap:false
    },
    {
        id:15,
        title:'생각은 부자처럼',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/consultanta100/87132/ProjectInfo/Cover/7dc6c1688e0148dda52733154ee0589f.png',
        eventDate: '2024-08-11',
        price: '무료',
        region: '서울/경기/인천',
        online: true,
        offline: true,
        views: 40,
        scrap:false
    }
    ,
    {
        id:16,
        title:'인플레이션을 방어 할 자산소득 만들기',
        imgUrl: 'https://eventusstorage.blob.core.windows.net/evs/Image/buja/85282/ProjectInfo/Cover/ef5156c9943a45988de1a55fd862f59f.jpg',
        eventDate: '2024-08-17',
        price: '10,000',
        region: '서울/경기/인천',
        online: true,
        offline: false,
        views: 148,
        scrap:false
    }

];

export default eventArr;
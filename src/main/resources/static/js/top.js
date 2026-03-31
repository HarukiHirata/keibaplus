const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;


async function searchSummary() {
    const requestBody = {
        raceDateFrom: document.getElementById("raceDateFrom").value,
        raceDateTo: document.getElementById("raceDateTo").value,
        kenshuNo: document.getElementById("kenshuNo").value,
        courseNo: document.getElementById("courseNo").value
    };

    try {
        const response = await fetch('/api/shuushisummary/search', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            throw new Error("集計結果の取得に失敗しました");
        }

        const data = await response.json();

        document.getElementById("totalKounyuuKingaku").textContent = data.totalKounyuuKingaku;
        document.getElementById("totalHaraimodoshi").textContent = data.totalHaraimodoshi;

        let kounyuu = data.totalKounyuuKingaku;
        let haraimodoshi = data.totalHaraimodoshi;

        let rate = 0;

        if (kounyuu && kounyuu !== 0) {
            rate = (haraimodoshi / kounyuu) * 100;
        }

        document.getElementById("kaishuuritsu").textContent = rate.toFixed(2);

    } catch (error) {
        console.error(error);
        document.getElementById("totalKounyuuKingaku").textContent = "取得失敗";
        document.getElementById("totalHaraimodoshi").textContent = "取得失敗";
    }
}

window.addEventListener("load", searchSummary);

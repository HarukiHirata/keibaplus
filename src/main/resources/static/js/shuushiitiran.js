const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;
async function search() {
    const requestBody = {
        raceDateFrom: document.getElementById("raceDateFrom").value,
        raceDateTo: document.getElementById("raceDateTo").value,
        kenshuNo: document.getElementById("kenshuNo").value,
        courseNo: document.getElementById("courseNo").value
    };

    const resultBody = document.getElementById("resultBody");

    try {
        const response = await fetch('/api/shuushisummary/itiran', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                [csrfHeader]: csrfToken
            },
            body: JSON.stringify(requestBody)
        });

        if (!response.ok) {
            throw new Error("検索結果の取得に失敗しました");
        }

        const data = await response.json();

        resultBody.innerHTML = "";

        if (!data || data.length === 0) {
        resultBody.innerHTML = `
            <tr>
            <td colspan="8" class="text-center">検索結果は0件です</td>
            </tr>
        `;
        return;
        }

        data.forEach(row => {
        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td>${row.kenshuName ?? ""}</td>
            <td>${row.courseName ?? ""}</td>
            <td>${row.raceDate ?? ""}</td>
            <td>${row.raceNo ?? ""}</td>
            <td>${row.kounyuuKingaku ?? ""}</td>
            <td>${row.haraimodoshi ?? ""}</td>
            <td>
            <a href="/shuushihenshuu/${row.shuushiNo}" class="btn btn-info">編集</a>
            </td>
            <td>削除</td>
        `;
        resultBody.appendChild(tr);
        });


    } catch (error) {
        console.error(error);
        resultBody.innerHTML = `
        <tr>
            <td colspan="8" class="text-center text-danger">検索中にエラーが発生しました</td>
        </tr>
        `;
    }
}

window.addEventListener("load", search);


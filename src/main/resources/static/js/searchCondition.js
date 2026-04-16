const target = document.getElementById('searchConditionArea');
const icon = document.getElementById('arrowIcon');

target.addEventListener('show.bs.collapse', () => {
  icon.style.transform = 'rotate(180deg)';
});

target.addEventListener('hide.bs.collapse', () => {
  icon.style.transform = 'rotate(0deg)';
});